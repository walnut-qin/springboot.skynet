package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.StatisticController;
import com.kaos.skynet.api.mapper.common.ComPatientInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;
import com.kaos.skynet.entity.inpatient.ComBedInfo;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.entity.inpatient.FinSpecialCityPatient;
import com.kaos.skynet.entity.inpatient.escort.EscortAnnexInfo;
import com.kaos.skynet.entity.pipe.lis.LisResultNew;
import com.kaos.skynet.enums.impl.inpatient.InStateEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/escort/statistic")
public class StatisticControllerImpl implements StatisticController {
    /**
     * 日志模块
     */
    Logger logger = Logger.getLogger(StatisticControllerImpl.class);

    /**
     * 陪护业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 住院患者特殊标识信息cache
     */
    @Autowired
    Cache<String, FinSpecialCityPatient> specialCityPatientCache;

    /**
     * 床位cache
     */
    @Autowired
    Cache<String, ComBedInfo> bedInfoCache;

    /**
     * 患者信息接口
     */
    @Autowired
    ComPatientInfoMapper patientInfoMapper;

    /**
     * LIS数据接口
     */
    @Autowired
    Cache<String, LisResultNew> covidCache;

    /**
     * 院外核酸接口
     */
    @Autowired
    Cache<String, EscortAnnexInfo> escortAnnexCheckedCache;

    @Override
    @RequestMapping(value = "queryEscortData", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryEscortRsp> queryEscortData(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 查询指定科室的在院患者
        var pats = this.inMainInfoMapper.queryInpatients(null, null, deptCode, Lists.newArrayList(InStateEnum.病房接诊));

        // 过滤部分患者
        var filteredPats = pats.stream().filter((x) -> {
            // 检索特殊标识
            var specialFlag = this.specialCityPatientCache.getValue(x.inpatientNo);
            if (specialFlag != null) {
                switch (Optional.fromNullable(specialFlag.isSpecial).or("0")) {
                    case "0":
                        break;

                    default:
                        return false;
                }
            }
            // 门诊医保
            if (x.extFlag2 != null && x.extFlag2.equals("4")) {
                return false;
            }
            return true;
        }).toList();

        // 声明结果集
        List<QueryEscortRsp> ret = Lists.newArrayList();

        // 时间格式工具
        var formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 构造结果集
        for (FinIprInMainInfo inMainInfo : filteredPats) {
            // 构造结果元素
            QueryEscortRsp item = new QueryEscortRsp();
            item.inDate = inMainInfo.inDate;
            var bed = this.bedInfoCache.getValue(inMainInfo.bedNo);
            if (bed != null) {
                item.bedNo = bed.getBriefBedNo();
            }
            item.name = inMainInfo.name;
            item.cardNo = inMainInfo.cardNo;
            var patient = this.patientInfoMapper.queryPatientInfo(inMainInfo.cardNo);
            if (patient != null) {
                item.healthCode = patient.healthCode;
                item.travelCode = patient.travelCode;
                if (patient.highRiskFlag != null) {
                    item.highRiskFlag = patient.highRiskFlag ? "是" : "否";
                }
                item.highRiskArea = patient.highRiskArea;
            }
            var lisResult = this.covidCache.getValue(inMainInfo.cardNo);
            if (lisResult == null) {
                lisResult = this.covidCache.getValue(inMainInfo.patientNo);
            }
            if (lisResult != null) {
                item.nucleicAcidResult = String.format("%s(%s)", lisResult.result,
                        formater.format(lisResult.inspectDate));
            }
            ret.add(item);
            // 检索陪护
            var escorts = this.escortService.queryHelperInfos(inMainInfo.cardNo);
            if (escorts.size() >= 1) {
                var escort = escorts.get(0);
                var helper = this.patientInfoMapper.queryPatientInfo(escort.helperCardNo);
                if (helper != null) {
                    item.escort1Name = helper.name;
                    item.escort1CardNo = helper.cardNo;
                    item.escort1IdenNo = helper.identityCardNo;
                    var helperRet = this.covidCache.getValue(helper.cardNo);
                    if (helperRet != null) {
                        item.escort1NucleicAcidResult = String.format("%s(%s)", helperRet.result,
                                formater.format(helperRet.inspectDate));
                    } else {
                        var helperAnnexRet = this.escortAnnexCheckedCache.getValue(helper.cardNo);
                        if (helperAnnexRet != null) {
                            item.escort1NucleicAcidResult = String.format("%s(%s)",
                                    helperAnnexRet.associateEntity.escortAnnexChk.negativeFlag ? "阴性" : "阳性",
                                    formater.format(helperAnnexRet.associateEntity.escortAnnexChk.inspectDate));
                        }
                    }
                    item.escort1Tel = helper.linkmanTel;
                    item.escort1HealthCode = helper.healthCode;
                    item.escort1TravelCode = helper.travelCode;
                    if (helper.highRiskFlag != null) {
                        item.escort1HighRiskFlag = helper.highRiskFlag ? "是" : "否";
                    }
                    item.escort1HighRiskArea = helper.highRiskArea;
                }
            }
            if (escorts.size() >= 2) {
                var escort = escorts.get(1);
                var helper = this.patientInfoMapper.queryPatientInfo(escort.helperCardNo);
                if (helper != null) {
                    item.escort2Name = helper.name;
                    item.escort2CardNo = helper.cardNo;
                    item.escort2IdenNo = helper.identityCardNo;
                    var helperRet = this.covidCache.getValue(helper.cardNo);
                    if (helperRet != null) {
                        item.escort2NucleicAcidResult = String.format("%s(%s)", helperRet.result,
                                formater.format(helperRet.inspectDate));
                    } else {
                        var helperAnnexRet = this.escortAnnexCheckedCache.getValue(helper.cardNo);
                        if (helperAnnexRet != null) {
                            item.escort1NucleicAcidResult = String.format("%s(%s)",
                                    helperAnnexRet.associateEntity.escortAnnexChk.negativeFlag ? "阴性" : "阳性",
                                    formater.format(helperAnnexRet.associateEntity.escortAnnexChk.inspectDate));
                        }
                    }
                    item.escort2Tel = helper.linkmanTel;
                    item.escort2HealthCode = helper.healthCode;
                    item.escort2TravelCode = helper.travelCode;
                    if (helper.highRiskFlag != null) {
                        item.escort2HighRiskFlag = helper.highRiskFlag ? "是" : "否";
                    }
                    item.escort2HighRiskArea = helper.highRiskArea;
                }
            }
        }

        return ret;
    }
}

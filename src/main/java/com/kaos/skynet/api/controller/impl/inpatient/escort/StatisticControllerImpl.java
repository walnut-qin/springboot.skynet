package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.StatisticController;
import com.kaos.skynet.api.entity.inpatient.ComBedInfo;
import com.kaos.skynet.api.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.entity.inpatient.FinSpecialCityPatient;
import com.kaos.skynet.api.entity.inpatient.escort.EscortAnnexInfo;
import com.kaos.skynet.api.entity.pipe.lis.LisResultNew;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.api.mapper.common.ComPatientInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

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

    /**
     * 检索核酸结果
     * 
     * @param key 卡号或住院号
     * @return
     */
    private String queryNucleicAcidResult(String key) {
        // 检索院内LIS系统核酸数据
        LisResultNew lisResult = this.covidCache.getValue(key);

        // 检索院外上传的报告
        EscortAnnexInfo annexInfo = this.escortAnnexCheckedCache.getValue(key);

        /**
         * 定义结果集结构
         */
        @Data
        class Result {
            /**
             * 核酸结果内容
             */
            private String resultString = null;

            /**
             * 结果时间
             */
            private LocalDateTime operDate = null;
        }

        List<Result> rs = Lists.newArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (lisResult != null) {
            rs.add(new Result() {
                {
                    setResultString(String.format("%s(%s)", lisResult.result, formatter.format(lisResult.inspectDate)));
                    setOperDate(lisResult.inspectDate);
                }
            });
        }
        if (annexInfo != null) {
            rs.add(new Result() {
                {
                    var chk = annexInfo.associateEntity.escortAnnexChk;
                    setResultString(
                            String.format("%s(%s)", chk.negativeFlag ? "阴性" : "阳性", formatter.format(chk.inspectDate)));
                    setOperDate(chk.inspectDate);
                }
            });
        }

        if (rs.isEmpty()) {
            return null;
        } else {
            return rs.stream().sorted((x, y) -> {
                return y.getOperDate().compareTo(x.getOperDate());
            }).toList().get(0).resultString;
        }
    }

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
            item.nucleicAcidResult = this.queryNucleicAcidResult(inMainInfo.cardNo);
            if (item.nucleicAcidResult == null) {
                item.nucleicAcidResult = this.queryNucleicAcidResult(inMainInfo.patientNo);
            }
            // 检索陪护
            var escorts = this.escortService.queryHelperInfos(inMainInfo.cardNo);
            if (escorts.size() >= 1) {
                var escort = escorts.get(0);
                var helper = this.patientInfoMapper.queryPatientInfo(escort.helperCardNo);
                if (helper != null) {
                    item.escort1Name = helper.name;
                    item.escort1CardNo = helper.cardNo;
                    item.escort1IdenNo = helper.identityCardNo;
                    item.escort1NucleicAcidResult = this.queryNucleicAcidResult(helper.cardNo);
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
                    item.escort2NucleicAcidResult = this.queryNucleicAcidResult(helper.cardNo);
                    item.escort2Tel = helper.linkmanTel;
                    item.escort2HealthCode = helper.healthCode;
                    item.escort2TravelCode = helper.travelCode;
                    if (helper.highRiskFlag != null) {
                        item.escort2HighRiskFlag = helper.highRiskFlag ? "是" : "否";
                    }
                    item.escort2HighRiskArea = helper.highRiskArea;
                }
            }
            ret.add(item);
        }

        return ret;
    }
}

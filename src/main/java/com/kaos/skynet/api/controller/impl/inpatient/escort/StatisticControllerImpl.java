package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.impl.common.ComPatientInfoCache;
import com.kaos.skynet.api.cache.impl.inpatient.ComBedInfoCache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.StatisticController;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
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
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 床位cache
     */
    @Autowired
    ComBedInfoCache bedInfoCache;

    /**
     * 患者基本信息cache
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * LIS数据接口
     */
    @Autowired
    LisResultNewMapper lisResultMapper;

    @Override
    @RequestMapping(value = "queryEscortRsp", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryEscortRsp> queryEscortRsp(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 查询指定科室的在院患者
        var pats = this.inMainInfoMapper.queryInpatients(null, null, deptCode, Lists.newArrayList(InStateEnum.病房接诊));

        // 声明结果集
        List<QueryEscortRsp> ret = Lists.newArrayList();

        // 时间格式工具
        var formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 构造结果集
        for (FinIprInMainInfo inMainInfo : pats) {
            // 构造结果元素
            QueryEscortRsp item = new QueryEscortRsp();
            item.inDate = inMainInfo.inDate;
            var bed = this.bedInfoCache.getValue(inMainInfo.bedNo);
            if (bed != null) {
                item.bedNo = bed.getBriefBedNo();
            }
            var patient = this.patientInfoCache.getValue(inMainInfo.cardNo);
            if (patient != null) {
                item.name = patient.name;
                item.cardNo = patient.cardNo;
                item.healthCode = patient.healthCode.getDescription();
                item.travelCode = patient.travelCode.getDescription();
                item.highRiskFlag = patient.highRiskFlag;
                item.highRiskArea = patient.highRiskArea;
            }
            var lisResult = this.lisResultMapper.queryInspectResult(inMainInfo.patientNo, "SARS-CoV-2-RNA", null, null);
            if (lisResult != null && !lisResult.isEmpty()) {
                Collections.sort(lisResult, (x, y) -> {
                    return y.inspectDate.compareTo(x.inspectDate);
                });
                var test = lisResult.get(0);
                item.nucleicAcidResult = String.format("%s(%s)", test.result, formater.format(test.inspectDate));
            }
            ret.add(item);
        }

        return ret;
    }
}

package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.cache.impl.common.ComPatientInfoCache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.AnnexController;
import com.kaos.skynet.api.service.inf.inpatient.escort.AnnexService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping({ "/ms/inpatient/escort/annex", "/ms/inpatient/escort" })
public class AnnexControllerImpl implements AnnexController {
    /**
     * 日志模块
     */
    Logger logger = Logger.getLogger(AnnexControllerImpl.class);

    /**
     * 业务模块
     */
    @Autowired
    AnnexService escortAnnexService;

    /**
     * 患者基本信息cache
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    @Override
    @RequestMapping(value = "uploadAnnex", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String uploadAnnex(@NotNull(message = "陪护人卡号不能为空") String helperCardNo,
            @NotNull(message = "附件链接不能为空") String url) {
        // 入参记录
        this.logger.info(String.format("上传附件<helperCardNo = %s, url = %s>", helperCardNo, url));

        // 调用服务
        var annexInfo = this.escortAnnexService.uploadAnnex(helperCardNo, url);

        return annexInfo.annexNo;
    }

    @Override
    @RequestMapping(value = "checkAnnex", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void checkAnnex(@NotNull(message = "附件号不能为空") String annexNo,
            @NotNull(message = "审核人不能为空") String checker,
            @NotNull(message = "审核结果不能为空") Boolean negativeFlag,
            @NotNull(message = "检验时间不能为空") Date inspectDate) {
        // 入参记录
        this.logger.info(String.format("审核附件<annexNo = %s, checker = %s, negativeFlag = %s, inspectDate = %s>", annexNo,
                checker, negativeFlag.toString(), inspectDate.toString()));

        // 调用服务
        this.escortAnnexService.checkAnnex(annexNo, checker, negativeFlag, inspectDate);
    }

    @Override
    @RequestMapping(value = "queryAnnexInDept", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryAnnexInDeptRsp> queryAnnexInDept(@NotNull(message = "科室编码不能为空") String deptCode,
            @NotNull(message = "审核标识不能为空") Boolean checked) {
        // 入参记录
        this.logger.info(String.format("查询科室信息<deptCode = %s, checked = %s>", deptCode, checked.toString()));

        // 调用服务
        var annexInfos = this.escortAnnexService.queryAnnexInfoInDept(deptCode, checked);

        // 构造响应
        List<QueryAnnexInDeptRsp> rsps = Lists.newArrayList();
        for (var annexInfo : annexInfos) {
            QueryAnnexInDeptRsp rsp = new QueryAnnexInDeptRsp();
            rsp.annexNo = annexInfo.annexNo;
            rsp.helperName = annexInfo.associateEntity.patientInfo.name;
            rsp.picUrl = annexInfo.annexUrl;
            if (annexInfo.associateEntity.patientInfo != null) {
                if (annexInfo.associateEntity.patientInfo.associateEntity.escortedPatients != null) {
                    rsp.patientNames = annexInfo.associateEntity.patientInfo.associateEntity.escortedPatients.stream()
                            .map(x -> x.name).toList();
                }
            }
            if (annexInfo.associateEntity.escortAnnexChk != null) {
                rsp.negative = annexInfo.associateEntity.escortAnnexChk.negativeFlag;
                rsp.inspectDate = annexInfo.associateEntity.escortAnnexChk.inspectDate;
            }
            rsps.add(rsp);
        }

        return rsps;
    }
}

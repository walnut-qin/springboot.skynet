package com.kaos.skynet.api.controller.impl.inpatient.escort;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.escort.AnnexController;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.entity.common.ComPatientInfo;
import com.kaos.skynet.api.entity.inpatient.escort.EscortAnnexChk;
import com.kaos.skynet.api.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.skynet.api.service.inf.inpatient.escort.AnnexService;
import com.kaos.skynet.api.service.inf.inpatient.escort.EscortService;
import com.kaos.skynet.core.type.converter.string.bool.NumericStringToBooleanConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.time.LocalDateTime;

@Validated
@RestController
@RequestMapping({ "/ms/inpatient/escort/annex", "/ms/inpatient/escort" })
public class AnnexControllerImpl implements AnnexController {
    /**
     * 日志模块
     */
    Logger logger = Logger.getLogger(AnnexControllerImpl.class);

    /**
     * 转换器
     */
    Converter<String, Boolean> stringToBooleanConverter = new NumericStringToBooleanConverter();

    /**
     * 业务模块
     */
    @Autowired
    AnnexService annexService;

    /**
     * 业务模块
     */
    @Autowired
    EscortService escortService;

    /**
     * 附件mapper
     */
    @Autowired
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    /**
     * 审核
     */
    @Autowired
    EscortAnnexChkMapper escortAnnexChkMapper;

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
        var annexInfo = this.annexService.uploadAnnex(helperCardNo, url);

        // 查询所有关联陪护证
        var escorts = this.escortService.queryPatientInfos(helperCardNo);
        if (escorts != null && !escorts.isEmpty()) {
            for (var escort : escorts) {
                // 更新关联陪护状态
                synchronized (LockMgr.stateLock.get(escort.escortNo)) {
                    this.escortService.updateEscortState(escort.escortNo, null, "WebService", "患者上传外院报告");
                }
            }
        }

        return annexInfo.annexNo;
    }

    @Override
    @RequestMapping(value = "checkAnnex", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void checkAnnex(@NotNull(message = "附件号不能为空") String annexNo,
            @NotNull(message = "审核人不能为空") String checker,
            @NotNull(message = "审核结果不能为空") String negativeFlag,
            @NotNull(message = "检验时间不能为空") LocalDateTime inspectDate) {
        // 转换适配
        Boolean negativeFlagBoolean = stringToBooleanConverter.convert(negativeFlag);

        // 入参记录
        this.logger.info(String.format("审核附件<annexNo = %s, checker = %s, negativeFlag = %s, inspectDate = %s>", annexNo,
                checker, negativeFlag.toString(), inspectDate.toString()));

        // 加状态操作锁，防止同时操作同一个陪护证
        synchronized (LockMgr.annexLock.get(annexNo)) {
            // 调用业务
            this.annexService.checkAnnex(annexNo, checker, negativeFlagBoolean, inspectDate);
        }

        // 检索附件信息
        var annex = this.escortAnnexInfoMapper.queryAnnexInfo(annexNo);

        // 查询所有关联陪护证
        var escorts = this.escortService.queryPatientInfos(annex.cardNo);
        if (escorts != null && !escorts.isEmpty()) {
            for (var escort : escorts) {
                // 更新关联陪护状态
                synchronized (LockMgr.stateLock.get(escort.escortNo)) {
                    this.escortService.updateEscortState(escort.escortNo, null, "WebService", "患者上传外院报告");
                }
            }
        }
    }

    @Override
    @RequestMapping(value = "queryAnnexInDept", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryAnnexInDeptRsp> queryAnnexInDept(@NotNull(message = "科室编码不能为空") String deptCode,
            @NotNull(message = "审核标识不能为空") String checked) {
        // 转换适配
        Boolean checkedBoolean = stringToBooleanConverter.convert(checked);

        // 入参记录
        this.logger.info(String.format("查询科室信息<deptCode = %s, checked = %s>", deptCode, checked.toString()));

        // 调用服务
        var annexInfos = this.annexService.queryAnnexInfoInDept(deptCode, checkedBoolean);

        // 构造响应
        List<QueryAnnexInDeptRsp> rsps = Lists.newArrayList();
        for (var annexInfo : annexInfos) {
            QueryAnnexInDeptRsp rsp = new QueryAnnexInDeptRsp();
            rsp.annexNo = annexInfo.annexNo;
            rsp.picUrl = "http://172.16.100.252:8025/ms/inpatient/escort/annex/getPic?refer=".concat(annexInfo.annexNo);
            // 患者基本信息
            ComPatientInfo helper = patientInfoCache.get(annexInfo.cardNo);
            if (helper != null) {
                rsp.helperName = helper.getName();
            }
            // 检索陪护的患者
            List<EscortMainInfo> escortInfos = escortService.queryPatientInfos(annexInfo.cardNo);
            if (escortInfos != null) {
                rsp.patientNames = escortInfos.stream().map((x) -> {
                    // 检索患者实体
                    ComPatientInfo patient = patientInfoCache.get(x.patientCardNo);
                    if (patient != null) {
                        return patient.getName();
                    }
                    return null;
                }).toList();
            }
            // 审核结果
            EscortAnnexChk check = escortAnnexChkMapper.queryAnnexChk(annexInfo.annexNo);
            if (check != null) {
                rsp.negative = check.negativeFlag;
                rsp.inspectDate = check.inspectDate;
            }
            rsps.add(rsp);
        }

        return rsps;
    }

    @RequestMapping(value = "getPic", method = RequestMethod.GET, produces = MediaType.JPEG)
    public BufferedImage getPic(@NotNull(message = "键值不能为空") String refer) {
        // 根据refer号获取annexUrl
        var rec = this.escortAnnexInfoMapper.queryAnnexInfo(refer);
        if (rec == null) {
            throw new RuntimeException("未查询到附件记录");
        }

        // 读取图片并返回
        try {
            return Thumbnails.of(new URL(rec.annexUrl)).scale(1.0f).asBufferedImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

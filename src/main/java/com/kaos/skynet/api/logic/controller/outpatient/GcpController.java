package com.kaos.skynet.api.logic.controller.outpatient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.cache.common.config.ConfigMultiMapCache;
import com.kaos.skynet.api.data.cache.outpatient.FinOprRegisterCache;
import com.kaos.skynet.api.data.enums.TransTypeEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.api.logic.controller.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "api/outpatient/gcp", "/ms/outpatient/gcp" })
public class GcpController {
    /**
     * 挂号缓存
     */
    @Autowired
    FinOprRegisterCache registerCache;

    /**
     * 患者信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 配置表缓存
     */
    @Autowired
    ConfigMultiMapCache multiMapCache;

    /**
     * 检测是否具有GCP权限
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "test", method = RequestMethod.POST, produces = MediaType.TEXT)
    public Boolean test(@RequestBody @NotNull(message = "body不能为空") TestReq req) {
        // 记录日志
        log.info("执行GCP权限检测");

        // 获取患者挂号信息
        var register = registerCache.get(FinOprRegisterCache.Key.builder()
                .clinicCode(req.getClinicCode())
                .transType(TransTypeEnum.Positive)
                .build());
        if (register == null) {
            log.info("未找到挂号信息!");
            return false;
        }

        // 获取患者基本信息
        var patientInfo = this.patientInfoCache.get(register.getCardNo());
        if (patientInfo == null) {
            log.info("未找到患者!");
            return false;
        }

        // 判断GCP标识
        if (!Optional.fromNullable(patientInfo.getGcpFlag()).or(false)) {
            log.info("gcp标识为false!");
            return false;
        }

        // 获取配置的gcp科室列表
        var keyBuilder = ConfigMultiMapCache.MasterCache.Key.builder();
        keyBuilder.name("GcpDept");
        keyBuilder.value(req.getDeptCode());
        var deptConfig = multiMapCache.getMasterCache().get(keyBuilder.build());
        if (deptConfig == null) {
            log.info("非GCP科室!");
            return false;
        }

        return deptConfig.getValid() == ValidEnum.VALID;
    }

    /**
     * 请求body
     */
    @Getter
    @Builder
    @Validated
    public static class TestReq {
        /**
         * 科室编码
         */
        @NotBlank(message = "科室编码不能为空")
        private String deptCode;

        /**
         * 门诊号
         */
        @NotBlank(message = "门诊号不能为空")
        private String clinicCode;
    }
}

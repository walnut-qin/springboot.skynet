package com.kaos.skynet.api.controller.impl.outpatient;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.outpatient.GcpController;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.cache.common.config.ConfigMultiMapCache;
import com.kaos.skynet.api.data.cache.common.config.ConfigMultiMapCache.Key;
import com.kaos.skynet.api.data.cache.outpatient.FinOprRegisterCache;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/outpatient/gcp")
public class GcpControllerImpl implements GcpController {

    /**
     * 挂号信息接口
     */
    @Autowired
    FinOprRegisterCache registerCache;

    /**
     * 患者信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 复数键
     */
    @Autowired
    ConfigMultiMapCache.MasterCache configMultiMapMasterCache;

    @Override
    @RequestMapping(value = "test", method = RequestMethod.POST, produces = MediaType.TEXT)
    public Boolean test(@RequestBody @NotNull(message = "body不能为空") TestReq req) {
        // 记录日志
        log.info("执行GCP权限检测");

        // 获取患者挂号信息
        var register = this.registerCache.get(new FinOprRegisterCache.Key() {
            {
                setClinicCode(req.clinicCode);
                setTransType(TransTypeEnum.Positive);
            }
        });
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
        var deptConfig = configMultiMapMasterCache.get(new Key("GcpDept", req.deptCode));
        if (deptConfig == null) {
            log.info("非GCP科室!");
            return false;
        }

        return deptConfig.getValid() == ValidEnum.VALID;
    }
}

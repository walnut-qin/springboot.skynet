package com.kaos.his.controller.impl.outpatient;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.kaos.his.cache.Cache;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.outpatient.GcpController;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.entity.common.config.ConfigMap;
import com.kaos.his.enums.impl.common.TransTypeEnum;
import com.kaos.his.enums.impl.common.ValidStateEnum;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/outpatient/gcp")
public class GcpControllerImpl implements GcpController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(GcpControllerImpl.class);

    /**
     * 挂号信息接口
     */
    @Autowired
    FinOprRegisterMapper registerMapper;

    /**
     * 患者信息缓存
     */
    @Autowired
    Cache<String, ComPatientInfo> patientInfoCache;

    /**
     * 开关cache
     */
    @Autowired
    Cache<String, Cache<String, ConfigMap>> multiMapCache;

    @Override
    @RequestMapping(value = "test", method = RequestMethod.POST, produces = MediaType.TEXT)
    public Boolean test(@RequestBody @NotNull(message = "body不能为空") TestReq req) {
        // 记录日志
        this.logger.info("执行GCP权限检测");

        // 获取患者挂号信息
        var register = this.registerMapper.queryRegisterRec(req.clinicCode, TransTypeEnum.Positive);
        if (register == null) {
            this.logger.info("未找到挂号信息!");
            return false;
        }

        // 获取患者基本信息
        var patientInfo = this.patientInfoCache.getValue(register.cardNo);
        if (patientInfo == null) {
            this.logger.info("未找到患者!");
            return false;
        }

        // 判断GCP标识
        if (!Optional.fromNullable(patientInfo.gcpFlag).or(false)) {
            this.logger.info("gcp标识为false!");
            return false;
        }

        // 获取配置的gcp科室列表
        var deptSubCache = this.multiMapCache.getValue("GcpDept");
        if (deptSubCache == null) {
            this.logger.info("无GCP科室配置!");
            return false;
        }
        var deptConfig = deptSubCache.getValue(req.deptCode);
        if (deptConfig == null) {
            this.logger.info("非GCP科室!");
            return false;
        }

        return deptConfig.valid == ValidStateEnum.有效;
    }
}

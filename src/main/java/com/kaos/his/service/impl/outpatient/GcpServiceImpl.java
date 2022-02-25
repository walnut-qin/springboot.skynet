package com.kaos.his.service.impl.outpatient;

import com.google.common.base.Optional;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.enums.common.TransTypeEnum;
import com.kaos.his.enums.common.ValidStateEnum;
import com.kaos.his.mapper.common.config.ConfigMapMapper;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.his.service.inf.outpatient.GcpService;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GcpServiceImpl implements GcpService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(GcpServiceImpl.class.getName());

    /**
     * 配置列表接口
     */
    @Autowired
    ConfigMapMapper configMapMapper;

    /**
     * 门诊患者接口
     */
    @Autowired
    FinOprRegisterMapper registerMapper;

    /**
     * 患者基本信息缓存
     */
    @Autowired
    ICache<String, ComPatientInfo> patientInfoCache;

    @Override
    public Boolean checkGcpPrivilege(String deptCode, String clinicCode) {
        // 获取配置科室
        var cfg = this.configMapMapper.queryMultiMapItemValue("GcpDept", deptCode);
        if (cfg == null || cfg.valid != ValidStateEnum.有效) {
            return false;
        }

        // 获取门诊实体
        var register = this.registerMapper.queryRegisterRec(clinicCode, TransTypeEnum.Positive);
        if (register == null) {
            return false;
        } else {
            var patient = this.patientInfoCache.getValue(register.cardNo);
            if (patient == null) {
                return false;
            } else {
                return Optional.fromNullable(patient.gcpFlag).or(false);
            }
        }
    }
}

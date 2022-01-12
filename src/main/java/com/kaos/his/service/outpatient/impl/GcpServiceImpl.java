package com.kaos.his.service.outpatient.impl;

import com.kaos.his.enums.TransTypeEnum;
import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.config.ConfigMapMapper;
import com.kaos.his.mapper.outpatient.OutpatientMapper;
import com.kaos.his.service.outpatient.GcpService;

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
    OutpatientMapper outpatientMapper;

    @Override
    public Boolean checkGcpPrivilege(String deptCode, String clinicCode) {
        // 获取配置科室
        var cfg = this.configMapMapper.queryMultiMapItemValue("GcpDept", deptCode);
        if (cfg == null || cfg.valid != ValidStateEnum.有效) {
            return false;
        }

        // 获取门诊实体
        var outpatient = this.outpatientMapper.queryOutpatient(clinicCode, TransTypeEnum.Positive);
        if (outpatient == null || !outpatient.gcpFlag) {
            return false;
        }

        return true;
    }
}

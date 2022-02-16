package com.kaos.his.service.outpatient.impl;

import java.util.Date;

import com.kaos.his.cache.common.ComPatientInfoCache;
import com.kaos.his.cache.common.DawnOrgDeptCache;
import com.kaos.his.cache.common.DawnOrgEmplCache;
import com.kaos.his.enums.common.ValidStateEnum;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.his.service.outpatient.RegService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class RegServiceImpl implements RegService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(RegServiceImpl.class);

    /**
     * 挂号数据接口
     */
    @Autowired
    FinOprRegisterMapper registerMapper;

    /**
     * 患者基本信息cache
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 职工信息cache
     */
    @Autowired
    DawnOrgEmplCache dawnOrgEmplCache;

    /**
     * 科室信息cache
     */
    @Autowired
    DawnOrgDeptCache dawnOrgDeptCache;

    @Override
    public void freeRegister(String cardNo, String idenNo, String deptCode, String doctCode, Date seeDate) {
        // 获取患者基本信息
        var patientInfo = this.patientInfoCache.getValue(cardNo);
        if (patientInfo == null || patientInfo.isValid != ValidStateEnum.有效) {
            throw new RuntimeException(String.format("未检索到有效患者(%s)基本信息", cardNo));
        }

        // 检索挂号科室信息
        var dept = this.dawnOrgDeptCache.getValue(deptCode);
        if (dept == null || dept.valid != ValidStateEnum.有效) {
            throw new RuntimeException(String.format("未检索到有效科室(%s)基本信息", deptCode));
        }

        // 检索医生信息
        var doct = this.dawnOrgEmplCache.getValue(doctCode);
        if (doct == null || doct.valid != ValidStateEnum.有效) {
            throw new RuntimeException(String.format("未检索到有效医生(%s)基本信息", doctCode));
        }

        // 检索必要序列
    }
}

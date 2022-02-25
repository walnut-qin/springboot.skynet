package com.kaos.his.service.impl.outpatient;

import java.util.Date;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.entity.outpatient.FinOprRegister;
import com.kaos.his.enums.impl.common.PayModeEnum;
import com.kaos.his.enums.impl.common.TransTypeEnum;
import com.kaos.his.enums.impl.common.ValidStateEnum;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.his.service.inf.outpatient.RegisterService;
import com.kaos.util.DateHelpers;
import com.kaos.util.helper.DateHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class RegisterServiceImpl implements RegisterService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(RegisterServiceImpl.class);

    /**
     * 挂号数据接口
     */
    @Autowired
    FinOprRegisterMapper registerMapper;

    /**
     * 患者基本信息cache
     */
    @Autowired
    Cache<String, ComPatientInfo> patientInfoCache;

    /**
     * 职工信息cache
     */
    @Autowired
    Cache<String, DawnOrgEmpl> dawnOrgEmplCache;

    /**
     * 科室信息cache
     */
    @Autowired
    Cache<String, DawnOrgDept> dawnOrgDeptCache;

    DateHelper dateHelper = DateHelpers.newDateHelper();

    @Transactional
    @Override
    public void freeRegister(String cardNo, String deptCode, String doctCode, Date seeDate, String opercode,
            PayModeEnum payMode) {
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

        // 构造挂号记录
        var register = new FinOprRegister();
        register.transType = TransTypeEnum.Positive;
        register.cardNo = cardNo;
        register.regDate = new Date();
        register.noon = this.dateHelper.getNoon(seeDate);
        register.name = patientInfo.name;
        register.idenNo = patientInfo.identityCardNo;
        register.sex = patientInfo.sex;
        register.birthday = patientInfo.birthday;
        register.cardType = "01";
        register.payKindCode = "01";
        register.pactCode = "1";
        register.pactName = "自费";
        register.regLevlCode = "06";
        register.regLevlName = "网络减免号";
        register.deptCode = deptCode;
        register.deptName = dept.deptName;
        register.orderNo = "0";
        register.beginTime = seeDate;
        register.endTime = seeDate;
        register.doctCode = doctCode;
        register.doctName = doct.emplName;
        register.ynRegCharge = true;
        register.recipeNo = "7";
        register.ynBook = "0";
        register.ynFr = "1";
        register.appenFlag = false;
        register.regFee = 0.0;
        register.checkFee = 0.0;
        register.diagFee = 0.0;
        register.otherFee = 0.0;
        register.ownCost = 0.0;
        register.pubCost = 0.0;
        register.payCost = 0.0;
        register.validFlag = ValidStateEnum.有效;
        register.operCode = opercode;
        register.operDate = new Date();
        register.checkFlag = false;
        register.ynSee = false;
        register.seeDate = seeDate;
        register.sendInHosCaseFlag = false;
        register.encryptNameFlag = false;
        register.ecoCost = 0.0;
        register.isAccount = false;
        register.emergencyFlag = false;
        register.deptOwn = dept.deptOwn;
        register.payMode = payMode;

        // 插入挂号记录
        this.registerMapper.insertRegisterRec(register);
    }
}

package com.kaos.his.service.outpatient.impl;

import java.util.List;

import com.kaos.his.cache.common.DawnOrgDeptCache;
import com.kaos.his.cache.common.DawnOrgEmplCache;
import com.kaos.his.enums.common.SysClassEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.TransTypeEnum;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.service.outpatient.FeeService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FeeServiceImpl implements FeeService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(GcpServiceImpl.class.getName());

    /**
     * 挂号接口
     */
    @Autowired
    FinOprRegisterMapper registerMapper;

    /**
     * 门诊费用接口
     */
    @Autowired
    FinOpbFeeDetailMapper feeDetailMapper;

    /**
     * 职工cache
     */
    DawnOrgEmplCache dawnOrgEmplCache = DawnOrgEmplCache.getCache();

    /**
     * 科室cache
     */
    DawnOrgDeptCache dawnOrgDeptCache = DawnOrgDeptCache.getCache();

    @Transactional
    @Override
    public void undrugPriced(String clinicCode, String operCode, List<String> itemCodes, String termNo) {
        // 查询挂号记录
        var register = this.registerMapper.queryRegisterRec(clinicCode, TransTypeEnum.Positive);
        if (register == null) {
            throw new RuntimeException("未查询到挂号记录, 无法划价");
        } else {
            register.associateEntity.regDept = this.dawnOrgDeptCache.getValue(register.deptCode);
            if (register.associateEntity.regDept == null) {
                throw new RuntimeException("未查询到挂号科室, 无法划价");
            }
        }

        // 查询公共字段
        var feeDetail = this.feeDetailMapper.queryPreInsertItem();
        if (feeDetail == null) {
            throw new RuntimeException("FinOpbFeeDetail插入准备出错");
        }

        for (int i = 0; i < itemCodes.size(); i++) {
            var itemCode = itemCodes.get(i);
            switch (itemCode) {
                case "F00000068033":
                    // 三院区区分操作
                    switch (register.associateEntity.regDept.deptOwn) {
                        case Sourth:
                            // 核酸检测门诊
                            feeDetail.regDeptCode = "2915";
                            feeDetail.execDeptCode = "5010";
                            feeDetail.execDeptName = "临床检验部";
                            break;

                        case North:
                            // N核酸检测门诊
                            feeDetail.regDeptCode = "2916";
                            feeDetail.execDeptCode = "5200";
                            feeDetail.execDeptName = "N医疗检验部";
                            break;

                        case East:
                            // E核酸检测门诊
                            feeDetail.regDeptCode = "2914";
                            feeDetail.execDeptCode = "5301";
                            feeDetail.execDeptName = "E临床检验科";
                            break;

                        default:
                            throw new RuntimeException("不支持的院区");
                    }
                    feeDetail.feeCode = MinFeeEnum.化验费;
                    feeDetail.classCode = SysClassEnum.检验;
                    break;

                case "F00000043248":
                case "F00000066485":
                case "F00000043231":
                case "F00000066506":
                    // 三院区区分操作
                    switch (register.associateEntity.regDept.deptOwn) {
                        case Sourth:
                            // 产科门诊
                            feeDetail.regDeptCode = "2132";
                            feeDetail.execDeptCode = "5050";
                            feeDetail.execDeptName = "超声医学科";
                            break;

                        case North:
                            // N核酸检测门诊
                            feeDetail.regDeptCode = "2845";
                            feeDetail.execDeptCode = "5204";
                            feeDetail.execDeptName = "N超声影像科";
                            break;

                        case East:
                            // E核酸检测门诊
                            feeDetail.regDeptCode = "2614";
                            feeDetail.execDeptCode = "5302";
                            feeDetail.execDeptName = "E超声影像科";
                            break;

                        default:
                            throw new RuntimeException("不支持的院区");
                    }
                    feeDetail.feeCode = MinFeeEnum.彩超;
                    feeDetail.classCode = SysClassEnum.检查;
                    break;

                default:
                    throw new RuntimeException("不支持的项目");
            }
        }
    }
}

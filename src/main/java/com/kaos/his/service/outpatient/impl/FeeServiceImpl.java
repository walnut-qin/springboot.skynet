package com.kaos.his.service.outpatient.impl;

import java.util.Date;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.entity.common.undrug.FinComUndrugInfo;
import com.kaos.his.entity.outpatient.fee.FinOpbFeeDetail;
import com.kaos.his.enums.common.SysClassEnum;
import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.enums.common.TransTypeEnum;
import com.kaos.his.enums.outpatient.fee.FeeDetailCancelFlagEnum;
import com.kaos.his.enums.outpatient.fee.FeeDetailCostSourceEnum;
import com.kaos.his.enums.outpatient.fee.FeeDetailPayFlagEnum;
import com.kaos.his.mapper.outpatient.FinOprRegisterMapper;
import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.service.outpatient.FeeService;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
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
    @Autowired
    ICache<String, DawnOrgEmpl> dawnOrgEmplCache;

    /**
     * 科室cache
     */
    @Autowired
    ICache<String, DawnOrgDept> dawnOrgDeptCache;

    /**
     * 非药品信息cache
     */
    @Autowired
    ICache<String, FinComUndrugInfo> undrugInfoCache;

    /**
     * 统计大小类对照cache
     */
    @Autowired
    ICache<Pair<ReportTypeEnum, MinFeeEnum>, FinComFeeCodeStat> feeCodeStatCache;

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

        // 依次对每一个项目操作
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

            // 检索项目信息
            var undrugInfo = this.undrugInfoCache.getValue(itemCode);
            if (undrugInfo == null) {
                throw new RuntimeException(String.format("未检索到非药品项目(%s)的信息", itemCode));
            }

            // 填充其他待插字段
            feeDetail.seqNo = i + 1;
            feeDetail.transType = TransTypeEnum.Positive;
            feeDetail.clinicCode = clinicCode;
            feeDetail.cardNo = register.cardNo;
            feeDetail.regDate = new Date();
            feeDetail.regDeptCode = register.deptCode;
            feeDetail.doctCode = register.doctCode;
            feeDetail.itemCode = itemCode;
            feeDetail.itemName = undrugInfo.itemName;
            feeDetail.drugFlag = false;
            feeDetail.specs = undrugInfo.spec;
            feeDetail.unitPrice = undrugInfo.unitPrice;
            feeDetail.qty = 1.0;
            feeDetail.days = 1;
            feeDetail.injectNumber = 0;
            feeDetail.emergencyFlag = false;
            feeDetail.doseOnce = 0.0;
            feeDetail.packQty = 1;
            feeDetail.priceUnit = "次";
            feeDetail.pubCost = 0.0;
            feeDetail.payCost = 0.0;
            feeDetail.unitPrice = undrugInfo.unitPrice;
            feeDetail.mainDrugFlag = false;
            feeDetail.operCode = operCode;
            feeDetail.operDate = new Date();
            feeDetail.payFlag = FeeDetailPayFlagEnum.划价;
            feeDetail.cancelFlag = FeeDetailCancelFlagEnum.正常;
            feeDetail.confirmFlag = false;
            feeDetail.newItemRate = 1.0;
            feeDetail.oldItemRate = 1.0;
            feeDetail.extFlag = true;
            feeDetail.extFlag3 = false;
            feeDetail.noBackNum = 1.0;
            feeDetail.confirmNum = 0.0;
            feeDetail.confirmInject = 0;
            feeDetail.ecoCost = 0.0;
            feeDetail.overCost = undrugInfo.unitPrice;
            feeDetail.excessCost = 0.0;
            feeDetail.drugOwnCost = 0.0;
            feeDetail.costSource = FeeDetailCostSourceEnum.操作员;
            feeDetail.subJobFlag = false;
            feeDetail.accountFlag = false;
            feeDetail.updateSequenceNo = 0;
            feeDetail.docInDept = register.deptCode;
            feeDetail.aTermNo = termNo;

            // 插入数据
            this.feeDetailMapper.insertFeeDetail(feeDetail);

            // 更新看诊标识
            this.registerMapper.updateSeeFlag(clinicCode, TransTypeEnum.Positive, true);
        }
    }

    public void saveFee(String clinicCode) {
        // 检索所有原始费用明细
        var feeDetails = this.feeDetailMapper.queryFeeDetailsWithClinicCode(clinicCode, null, null, null);

        // 加入过滤条件并按照统计大类分组
        Multimap<String, FinOpbFeeDetail> feeDetailMap = ArrayListMultimap.create();
        for (var feeDetail : feeDetails) {
            if (feeDetail.payFlag != FeeDetailPayFlagEnum.划价) {
                continue;
            } else if (feeDetail.cancelFlag != FeeDetailCancelFlagEnum.正常) {
                continue;
            }
            var feeCodeStat = this.feeCodeStatCache.getValue(new Pair<>(ReportTypeEnum.门诊发票, feeDetail.feeCode));
            if (feeCodeStat == null) {
                throw new RuntimeException(String.format("获取统计大类异常(recipeNo = %s)", feeDetail.recipeNo));
            }
            feeDetail.associateEntity.feeCodeStat = feeCodeStat;
            feeDetailMap.put(feeCodeStat.feeStatCate, feeDetail);
        }

        // 轮训所有统计大类
        for (var statCode : feeDetailMap.keySet()) {
            feeDetailMap.get(statCode);
        }
    }
}

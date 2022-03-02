package com.kaos.his.service.impl.inpatient.fee.report;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.ToDoubleFunction;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Sets;
import com.kaos.his.cache.impl.common.DawnOrgDeptCache;
import com.kaos.his.cache.impl.inpatient.FinIprInMainInfoCache;
import com.kaos.his.entity.inpatient.fee.FinIpbFeeInfo;
import com.kaos.his.entity.inpatient.fee.FinIpbItemList;
import com.kaos.his.entity.inpatient.fee.FinIpbMedicineList;
import com.kaos.his.mapper.inpatient.fee.FinIpbFeeInfoMapper;
import com.kaos.his.mapper.inpatient.fee.FinIpbItemListMapper;
import com.kaos.his.mapper.inpatient.fee.FinIpbMedicineListMapper;
import com.kaos.his.service.inf.inpatient.fee.report.ReconcileService;

import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReconcileServiceImpl implements ReconcileService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ReconcileServiceImpl.class);

    /**
     * 住院费用信息接口
     */
    @Autowired
    FinIpbFeeInfoMapper feeInfoMapper;

    /**
     * 非药品明细接口
     */
    @Autowired
    FinIpbItemListMapper itemListMapper;

    /**
     * 药品明细接口
     */
    @Autowired
    FinIpbMedicineListMapper medicineListMapper;

    /**
     * 科室cache
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    /**
     * 住院cache
     */
    @Autowired
    FinIprInMainInfoCache inMainInfoCache;

    /**
     * 检索HIS费用信息并归类
     * 
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    private Multimap<String, FinIpbFeeInfo> getHisInpatientIncome(Date beginDate, Date endDate) {
        // 查询原始费用信息
        var orgData = this.feeInfoMapper.queryFeeInfos(null, beginDate, endDate);

        return Multimaps.index(orgData, new Function<FinIpbFeeInfo, String>() {
            @Override
            public @Nullable String apply(@Nullable FinIpbFeeInfo input) {
                return input.executeDeptCode;
            }
        });
    }

    /**
     * 检索用友费用信息并归类
     * 
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    private Multimap<String, FinIpbItemList> getYongYouUndrugInpatientIncome(Date beginDate, Date endDate) {
        // 查询原始费用信息
        var orgData = this.itemListMapper.queryItemLists(null, beginDate, endDate);

        return Multimaps.index(orgData, new Function<FinIpbItemList, String>() {
            @Override
            public @Nullable String apply(@Nullable FinIpbItemList input) {
                return input.executeDeptCode;
            }
        });
    }

    /**
     * 检索用友费用信息并归类
     * 
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    private Multimap<String, FinIpbMedicineList> getYongYouDrugInpatientIncome(Date beginDate, Date endDate) {
        // 查询原始费用信息
        var orgData = this.medicineListMapper.queryMedicineLists(null, beginDate, endDate);

        return Multimaps.index(orgData, new Function<FinIpbMedicineList, String>() {
            @Override
            public @Nullable String apply(@Nullable FinIpbMedicineList input) {
                return input.executeDeptCode;
            }
        });
    }

    /**
     * 检查处方
     * 
     * @param recipeNo
     * @param feeInfos
     * @param undrugInfos
     * @param drugInfos
     */
    private Triplet<List<FinIpbFeeInfo>, List<FinIpbItemList>, List<FinIpbMedicineList>> checkInpatientIncomeInRecipe(
            String recipeNo,
            List<FinIpbFeeInfo> feeInfos,
            List<FinIpbItemList> undrugInfos,
            List<FinIpbMedicineList> drugInfos) {
        // 计算费用总和
        Double totFee = feeInfos.stream().mapToDouble(new ToDoubleFunction<FinIpbFeeInfo>() {
            @Override
            public double applyAsDouble(FinIpbFeeInfo arg0) {
                return arg0.totCost;
            }
        }).sum();
        // 计算非药品费用总和
        Double undrugFee = undrugInfos.stream().mapToDouble(new ToDoubleFunction<FinIpbItemList>() {
            @Override
            public double applyAsDouble(FinIpbItemList arg0) {
                return arg0.totCost;
            }
        }).sum();
        // 计算药品费用总和
        Double drugFee = drugInfos.stream().mapToDouble(new ToDoubleFunction<FinIpbMedicineList>() {
            @Override
            public double applyAsDouble(FinIpbMedicineList arg0) {
                return arg0.totCost;
            }
        }).sum();

        // 日志
        if (Math.abs(drugFee + undrugFee - totFee) > 1e-6) {
            return new Triplet<>(feeInfos, undrugInfos, drugInfos);
        }
        return null;
    }

    /**
     * 对比某个科室的数据
     * 
     * @param deptCode
     * @param feeInfos
     * @param undrugInfos
     * @param drugInfos
     */
    private Map<String, Triplet<List<FinIpbFeeInfo>, List<FinIpbItemList>, List<FinIpbMedicineList>>> checkInpatientIncomeInDept(
            String deptCode,
            Collection<FinIpbFeeInfo> feeInfos,
            Collection<FinIpbItemList> undrugInfos,
            Collection<FinIpbMedicineList> drugInfos) {
        // 记录科室信息
        var dept = this.deptCache.getValue(deptCode);
        if (dept == null) {
            this.logger.warn(String.format("获取科室<%s>信息失败, 跳过核对", deptCode));
            return null;
        }

        // 计算费用总和
        Double totFee = feeInfos.stream().mapToDouble(new ToDoubleFunction<FinIpbFeeInfo>() {
            @Override
            public double applyAsDouble(FinIpbFeeInfo arg0) {
                return arg0.totCost;
            }
        }).sum();
        // 计算非药品费用总和
        Double undrugFee = undrugInfos.stream().mapToDouble(new ToDoubleFunction<FinIpbItemList>() {
            @Override
            public double applyAsDouble(FinIpbItemList arg0) {
                return arg0.totCost;
            }
        }).sum();
        // 计算药品费用总和
        Double drugFee = drugInfos.stream().mapToDouble(new ToDoubleFunction<FinIpbMedicineList>() {
            @Override
            public double applyAsDouble(FinIpbMedicineList arg0) {
                return arg0.totCost;
            }
        }).sum();

        // 若数据异常，对比明细
        if (Math.abs(drugFee + undrugFee - totFee) > 1e-6) {
            // 费用按处方号归类
            var feeInfoDetails = Multimaps.index(feeInfos, new Function<FinIpbFeeInfo, String>() {
                @Override
                public @Nullable String apply(@Nullable FinIpbFeeInfo input) {
                    return input.recipeNo;
                }
            });

            // 非药品归类
            var undrugInfoDetails = Multimaps.index(undrugInfos, new Function<FinIpbItemList, String>() {
                @Override
                public @Nullable String apply(@Nullable FinIpbItemList input) {
                    return input.recipeNo;
                }
            });

            // 药品归类
            var drugInfoDetails = Multimaps.index(drugInfos, new Function<FinIpbMedicineList, String>() {
                @Override
                public @Nullable String apply(@Nullable FinIpbMedicineList input) {
                    return input.recipeNo;
                }
            });

            // 融合所有处方号
            Set<String> recipeNos = Sets.newHashSet();
            recipeNos.addAll(feeInfoDetails.keySet());
            recipeNos.addAll(undrugInfoDetails.keySet());
            recipeNos.addAll(drugInfoDetails.keySet());

            // 检查每个处方
            Map<String, Triplet<List<FinIpbFeeInfo>, List<FinIpbItemList>, List<FinIpbMedicineList>>> data = Maps
                    .newConcurrentMap();
            for (var recipeNo : recipeNos) {
                var item = this.checkInpatientIncomeInRecipe(recipeNo, feeInfoDetails.get(recipeNo),
                        undrugInfoDetails.get(recipeNo), drugInfoDetails.get(recipeNo));
                if (item != null) {
                    data.put(recipeNo, item);
                }
            }
            return data;
        }

        return null;
    }

    @Override
    public Map<String, Map<String, Triplet<List<FinIpbFeeInfo>, List<FinIpbItemList>, List<FinIpbMedicineList>>>> checkInpatientIncome(
            Date beginDate, Date endDate) {
        // 获取HIS数据
        var hisData = this.getHisInpatientIncome(beginDate, endDate);

        // 获取用友非药品数据
        var undrugData = this.getYongYouUndrugInpatientIncome(beginDate, endDate);

        // 获取用友药品数据
        var drugData = this.getYongYouDrugInpatientIncome(beginDate, endDate);

        // 获取所有涉及的科室
        Set<String> deptCodes = Sets.newHashSet();
        deptCodes.addAll(hisData.keySet());
        deptCodes.addAll(undrugData.keySet());
        deptCodes.addAll(drugData.keySet());

        // 对每个科室检查
        Map<String, Map<String, Triplet<List<FinIpbFeeInfo>, List<FinIpbItemList>, List<FinIpbMedicineList>>>> data = Maps
                .newConcurrentMap();
        for (var deptCode : deptCodes) {
            // 分析科室信息
            var item = this.checkInpatientIncomeInDept(deptCode, hisData.get(deptCode), undrugData.get(deptCode),
                    drugData.get(deptCode));
            // 纳入结果集
            if (item != null) {
                data.put(deptCode, item);
            }
        }
        return data;
    }
}

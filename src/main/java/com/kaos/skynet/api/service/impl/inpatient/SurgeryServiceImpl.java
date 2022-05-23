package com.kaos.skynet.api.service.impl.inpatient;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Maps;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.cache.common.DawnOrgEmplCache;
import com.kaos.skynet.api.data.cache.inpatient.ComBedInfoCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryStatusEnum;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsArrangeMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsItemMapper;
import com.kaos.skynet.api.service.inf.inpatient.SurgeryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurgeryServiceImpl implements SurgeryService {
    /**
     * 手术查询接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 项目接口
     */
    @Autowired
    MetOpsItemMapper metOpsItemMapper;

    /**
     * 手术安排接口
     */
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    /**
     * 住院接口
     */
    @Autowired
    FinIprInMainInfoCache.MasterCache inMainInfoCache;

    /**
     * 患者基本信息cache
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 科室信息缓存
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    /**
     * 职工接口
     */
    @Autowired
    DawnOrgEmplCache emplCache;

    /**
     * 手术间接口
     */
    @Autowired
    Cache<String, MetOpsRoom> metOpsRoomCache;

    /**
     * 床位接口
     */
    @Autowired
    ComBedInfoCache bedInfoCache;

    static class DeptOwnPredicate implements Predicate<MetOpsApply> {
        /**
         * 院区
         */
        DeptOwnEnum deptOwn = null;

        /**
         * 手术间
         */
        String roomNo = null;

        DeptOwnPredicate(DeptOwnEnum deptOwn, String roomNo) {
            this.deptOwn = deptOwn;
            this.roomNo = roomNo;
        }

        @Override
        public boolean test(MetOpsApply apply) {
            // 过滤手术室
            if (roomNo != null && !roomNo.equals("ALL")) {
                if (apply.associateEntity.room == null) {
                    return false;
                }
                if (!apply.associateEntity.room.roomName.equals(roomNo)) {
                    return false;
                }
            }

            // 过滤科室
            if (deptOwn != null && deptOwn != DeptOwnEnum.All) {
                if (apply.associateEntity.surgeryDept == null) {
                    return false;
                }
                var dept = apply.associateEntity.surgeryDept;
                if (!dept.getDeptOwn().equals(this.deptOwn)) {
                    return false;
                }
            }

            return true;
        }
    }

    class DeptCodePredicate implements Predicate<MetOpsApply> {
        /**
         * 院区
         */
        String deptCode = null;

        /**
         * 手术间
         */
        String roomNo = null;

        DeptCodePredicate(String deptCode, String roomNo) {
            this.deptCode = deptCode;
            this.roomNo = roomNo;
        }

        @Override
        public boolean test(MetOpsApply apply) {
            // 过滤手术室
            if (roomNo != null && !roomNo.equals("ALL")) {
                if (apply.associateEntity.room == null) {
                    return false;
                }
                if (!apply.associateEntity.room.roomName.equals(roomNo)) {
                    return false;
                }
            }

            // 过滤科室
            if (deptCode != null && !deptCode.equals("ALL")) {
                // 检索住院记录
                var inMainInfo = inMainInfoCache.get("ZY01".concat(apply.patientNo));
                if (inMainInfo == null) {
                    return false;
                }

                // 检索科室信息
                var dept = deptCache.get(inMainInfo.getDeptCode());
                if (dept == null) {
                    return false;
                }

                if (!dept.getDeptCode().equals(this.deptCode)) {
                    return false;
                }
            }

            return true;
        }
    }

    static class MetComparator implements Comparator<MetOpsApply> {
        @Override
        public int compare(MetOpsApply arg0, MetOpsApply arg1) {
            // 比较房间号
            String key1 = arg0.associateEntity.room == null ? "" : arg0.associateEntity.room.roomName;
            String key2 = arg1.associateEntity.room == null ? "" : arg1.associateEntity.room.roomName;
            Integer cmp = key1.compareTo(key2);

            // 若房间号相等，再比较时间
            if (cmp.equals(0)) {
                return arg0.preDate.compareTo(arg1.preDate);
            }

            return cmp;
        }
    }

    /**
     * 过滤手术
     * 
     * @param applies  手术列表
     * @param deptCode 科室
     * @param roomNo   手术间
     * @return
     */
    private List<MetOpsApply> filterAndSort(List<MetOpsApply> applies, String deptCode, String roomNo) {
        switch (deptCode) {
            case "1290":
                applies = applies.stream().filter(new DeptOwnPredicate(DeptOwnEnum.Sourth, roomNo))
                        .sorted(new MetComparator()).toList();
                break;

            case "1291":
                applies = applies.stream().filter(new DeptOwnPredicate(DeptOwnEnum.East, roomNo))
                        .sorted(new MetComparator()).toList();
                break;

            case "5206":
                applies = applies.stream().filter(new DeptOwnPredicate(DeptOwnEnum.North, roomNo))
                        .sorted(new MetComparator()).toList();
                break;

            case "4015":
            case "4106":
            case "4078":
            case "0901":
                applies = applies.stream().sorted(new MetComparator()).toList();
                break;

            default:
                // 查本科室
                applies = applies.stream().filter(new DeptCodePredicate(deptCode, roomNo)).sorted(new MetComparator())
                        .toList();
                break;
        }
        return applies;
    }

    @Override
    public List<MetOpsApply> queryApplies(String deptCode, String roomNo, LocalDateTime beginDate,
            LocalDateTime endDate, List<SurgeryStatusEnum> status) {
        // 初步查询目标手术
        var applies = this.metOpsApplyMapper.queryApplies(null, beginDate, endDate, status, null, ValidStateEnum.有效,
                null);
        for (var apply : applies) {
            // 实体：项目
            apply.associateEntity.metOpsItem = this.metOpsItemMapper.queryMetOpsItem(apply.operationNo, "S991");

            // 主刀医师
            apply.associateEntity.opsDoc = this.emplCache.get(apply.opsDocCode);

            // 手术科室
            apply.associateEntity.surgeryDept = this.deptCache.get(apply.surgeryDeptCode);

            // 实体：手术安排
            var arranges = this.metOpsArrangeMapper.queryMetOpsArranges(apply.operationNo, null);
            apply.associateEntity.metOpsArranges = Maps.uniqueIndex(arranges, (x) -> {
                return x.role;
            });
            for (var role : apply.associateEntity.metOpsArranges.keySet()) {
                var arrange = apply.associateEntity.metOpsArranges.get(role);
                arrange.associateEntity.metOpsApply = apply;
                arrange.associateEntity.employee = this.emplCache.get(arrange.emplCode);
            }
        }

        // 过滤
        return this.filterAndSort(applies, deptCode, roomNo);
    }
}

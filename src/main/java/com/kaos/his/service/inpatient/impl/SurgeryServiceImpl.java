package com.kaos.his.service.inpatient.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.collect.Maps;
import com.kaos.his.cache.common.ComPatientInfoCache;
import com.kaos.his.cache.common.DawnOrgDeptCache;
import com.kaos.his.cache.common.DawnOrgEmplCache;
import com.kaos.his.cache.inpatient.ComBedInfoCache;
import com.kaos.his.cache.inpatient.surgery.MetOpsRoomCache;
import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.enums.common.DeptOwnEnum;
import com.kaos.his.enums.common.ValidStateEnum;
import com.kaos.his.enums.inpatient.surgery.SurgeryStatusEnum;
import com.kaos.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsArrangeMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsItemMapper;
import com.kaos.his.service.inpatient.SurgeryService;

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
    FinIprInMainInfoMapper inMainInfoMapper;

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
    MetOpsRoomCache metOpsRoomCache;

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
            if (roomNo != null) {
                if (apply.associateEntity.room == null) {
                    return false;
                }
                if (!apply.associateEntity.room.roomName.equals(roomNo)) {
                    return false;
                }
            }

            // 过滤科室
            if (apply.associateEntity.inMainInfo == null) {
                return false;
            }
            var inMainInfo = apply.associateEntity.inMainInfo;
            if (inMainInfo.associateEntity.dept == null) {
                return false;
            }
            var dept = inMainInfo.associateEntity.dept;
            if (!dept.deptOwn.equals(this.deptOwn)) {
                return false;
            }

            return true;
        }
    }

    static class DeptCodePredicate implements Predicate<MetOpsApply> {
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
            if (roomNo != null) {
                if (apply.associateEntity.room == null) {
                    return false;
                }
                if (!apply.associateEntity.room.roomName.equals(roomNo)) {
                    return false;
                }
            }

            // 过滤科室
            if (apply.associateEntity.inMainInfo == null) {
                return false;
            }
            var inMainInfo = apply.associateEntity.inMainInfo;
            if (inMainInfo.associateEntity.dept == null) {
                return false;
            }
            var dept = inMainInfo.associateEntity.dept;
            if (!dept.deptCode.equals(this.deptCode)) {
                return false;
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
                applies = applies.stream().filter(new DeptCodePredicate(deptCode, roomNo))
                        .sorted(new MetComparator()).toList();
                break;
        }
        return applies;
    }

    @Override
    public List<MetOpsApply> queryApplies(String deptCode, String roomNo, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status) {
        // 初步查询目标手术
        var applies = this.metOpsApplyMapper.queryApplies(beginDate, endDate, status, ValidStateEnum.有效);
        for (var apply : applies) {
            // 实体：项目
            apply.associateEntity.metOpsItem = this.metOpsItemMapper.queryMetOpsItem(apply.operationNo, "S991");

            // 主刀医师
            apply.associateEntity.opsDoc = this.emplCache.getValue(apply.opsDocCode);

            // 实体：手术安排
            var arranges = this.metOpsArrangeMapper.queryMetOpsArranges(apply.operationNo, null);
            apply.associateEntity.metOpsArranges = Maps.uniqueIndex(arranges, (x) -> {
                return x.role;
            });
            for (var role : apply.associateEntity.metOpsArranges.keySet()) {
                var arrange = apply.associateEntity.metOpsArranges.get(role);
                arrange.associateEntity.metOpsApply = apply;
                arrange.associateEntity.employee = this.emplCache.getValue(arrange.emplCode);
            }

            // 实体：住院患者
            apply.associateEntity.inMainInfo = this.inMainInfoMapper.queryInMainInfo("ZY01" + apply.patientNo);
            if (apply.associateEntity.inMainInfo != null) {
                // 定位住院实体
                var inMainInfo = apply.associateEntity.inMainInfo;

                // 患者基本信息
                inMainInfo.associateEntity.patientInfo = this.patientInfoCache.getValue(inMainInfo.cardNo);

                // 住院科室
                inMainInfo.associateEntity.dept = this.deptCache.getValue(inMainInfo.deptCode);

                // 床位
                inMainInfo.associateEntity.bedInfo = this.bedInfoCache.getValue(inMainInfo.bedNo);
            }

            // 实体：房间
            apply.associateEntity.room = this.metOpsRoomCache.getValue(apply.roomId);
        }

        // 过滤
        return this.filterAndSort(applies, deptCode, roomNo);
    }

    @Override
    public String queryValidApplyNo(String patientNo, Date beginDate, Date endDate) {
        // 检索手术申请记录
        var applies = this.metOpsApplyMapper.queryPatientMetOpsApplies(patientNo, beginDate, endDate,
                ValidStateEnum.有效);
        if (applies == null || applies.size() == 0) {
            throw new RuntimeException("未查询到符合条件的手术申请记录");
        } else if (applies.size() >= 2) {
            throw new RuntimeException("查询到多条符合条件的手术申请记录");
        }

        return applies.get(0).operationNo;
    }
}

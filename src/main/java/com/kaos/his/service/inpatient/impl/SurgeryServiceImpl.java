package com.kaos.his.service.inpatient.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Maps;
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

    @Override
    public List<MetOpsApply> queryMetOpsAppliesInDept(String deptCode, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status) {
        // 声明结果集
        List<MetOpsApply> metOpsApplies = null;

        // 查询结果
        switch (deptCode) {
            case "1290":
                metOpsApplies = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.Sourth, beginDate,
                        endDate, status, ValidStateEnum.有效);
                break;

            case "1291":
                metOpsApplies = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.East, beginDate, endDate,
                        status, ValidStateEnum.有效);
                break;

            case "5206":
                metOpsApplies = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.North, beginDate,
                        endDate, status, ValidStateEnum.有效);

            case "4015":
            case "4106":
            case "4078":
            case "0901":
                metOpsApplies = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.All, beginDate, endDate,
                        status, ValidStateEnum.有效);

            default:
                metOpsApplies = this.metOpsApplyMapper.queryMetOpsAppliesInDept(deptCode, beginDate, endDate, status,
                        ValidStateEnum.有效);
                break;
        }

        // 填充关联实体
        for (MetOpsApply apply : metOpsApplies) {
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

                // 住院科室
                inMainInfo.associateEntity.dept = this.deptCache.getValue(inMainInfo.deptCode);

                // 床位
                inMainInfo.associateEntity.bedInfo = this.bedInfoCache.getValue(inMainInfo.bedNo);
            }

            // 实体：房间
            apply.associateEntity.room = this.metOpsRoomCache.getValue(apply.roomId);
        }

        // 按手术室排序
        metOpsApplies.sort(new Comparator<MetOpsApply>() {
            @Override
            public int compare(MetOpsApply arg0, MetOpsApply arg1) {
                String key1 = arg0.associateEntity.room == null ? "" : arg0.associateEntity.room.roomName;
                String key2 = arg1.associateEntity.room == null ? "" : arg1.associateEntity.room.roomName;
                return key1.compareTo(key2);
            }
        });

        return metOpsApplies;
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

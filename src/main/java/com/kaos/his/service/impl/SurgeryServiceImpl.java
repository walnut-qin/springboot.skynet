package com.kaos.his.service.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.kaos.his.entity.inpatient.Inpatient;
import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.SurgeryStatusEnum;
import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.DepartmentMapper;
import com.kaos.his.mapper.common.EmployeeMapper;
import com.kaos.his.mapper.inpatient.BedMapper;
import com.kaos.his.mapper.inpatient.InpatientMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsArrangeMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsItemMapper;
import com.kaos.his.mapper.inpatient.surgery.MetOpsRoomMapper;
import com.kaos.his.service.SurgeryService;

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
    InpatientMapper inpatientMapper;

    /**
     * 科室接口
     */
    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 职工接口
     */
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * 手术间接口
     */
    @Autowired
    MetOpsRoomMapper metOpsRoomMapper;

    /**
     * 床位接口
     */
    @Autowired
    BedMapper bedMapper;

    @Override
    public List<MetOpsApply> queryMetOpsAppliesInDept(String deptCode, Date beginDate, Date endDate,
            List<SurgeryStatusEnum> status) {
        // 声明结果集
        List<MetOpsApply> rs = null;

        // 查询结果
        switch (deptCode) {
            case "1290":
                rs = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.Sourth, beginDate, endDate, status,
                        ValidStateEnum.有效);
                break;

            case "1291":
                rs = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.East, beginDate, endDate, status,
                        ValidStateEnum.有效);
                break;

            case "5206":
                rs = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.North, beginDate, endDate, status,
                        ValidStateEnum.有效);

            case "4015":
            case "4106":
            case "4078":
            case "0901":
                rs = this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.All, beginDate, endDate, status,
                        ValidStateEnum.有效);

            default:
                rs = this.metOpsApplyMapper.queryMetOpsAppliesInDept(deptCode, beginDate, endDate, status,
                        ValidStateEnum.有效);
                break;
        }

        // 填充关联实体
        for (MetOpsApply item : rs) {
            // 实体：项目
            item.associateEntity.metOpsItem = this.metOpsItemMapper.queryMetOpsItem(item.operationNo, "S991");

            // 主刀医师
            item.associateEntity.opsDoctor = this.employeeMapper.queryEmployee(item.opsDocCode, ValidStateEnum.有效);
            if (item.associateEntity.opsDoctor == null) {
                item.associateEntity.opsDoctor = this.employeeMapper.queryInformalEmployee(item.opsDocCode,
                        ValidStateEnum.有效);
            }

            // 实体：手术安排
            item.associateEntity.metOpsArranges = this.metOpsArrangeMapper.queryMetOpsArranges(item.operationNo, null);
            for (var iter : item.associateEntity.metOpsArranges) {
                iter.associateEntity.employee = this.employeeMapper.queryEmployee(iter.emplCode, ValidStateEnum.有效);
                if (iter.associateEntity.employee == null) {
                    iter.associateEntity.employee = this.employeeMapper.queryInformalEmployee(iter.emplCode,
                            ValidStateEnum.有效);
                }
            }

            // 实体：住院患者
            item.associateEntity.inpatient = this.inpatientMapper.queryInpatient("ZY01" + item.patientNo);
            if (item.associateEntity.inpatient != null) {
                // 锚点
                Inpatient inpatient = item.associateEntity.inpatient;

                // 住院科室
                inpatient.associateEntity.stayedDept = this.departmentMapper.queryDepartment(inpatient.stayedDeptCode,
                        ValidStateEnum.有效);

                // 床位
                inpatient.associateEntity.bed = this.bedMapper.queryBed(inpatient.bedNo, ValidStateEnum.有效);
            }
            // 实体：房间
            item.associateEntity.room = this.metOpsRoomMapper.queryMetOpsRoom(item.roomId);
        }

        // 按手术室排序
        rs.sort(new Comparator<MetOpsApply>() {
            @Override
            public int compare(MetOpsApply arg0, MetOpsApply arg1) {
                String key1 = arg0.associateEntity.room == null ? "" : arg0.associateEntity.room.roomName;
                String key2 = arg1.associateEntity.room == null ? "" : arg1.associateEntity.room.roomName;
                return key1.compareTo(key2);
            }
        });

        return rs;
    }
}

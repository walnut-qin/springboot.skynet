package com.kaos.skynet.api.mapper.common;

import java.util.List;

import com.kaos.skynet.api.entity.common.DawnOrgDept;
import com.kaos.skynet.api.enums.common.DeptOwnEnum;
import com.kaos.skynet.api.enums.common.DeptTypeEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;

public interface DawnOrgDeptMapper {
    /**
     * 主键查询
     * 
     * @param deptCode 科室编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnOrgDept queryDepartment(String deptCode);

    /**
     * 查询科室列表
     * 
     * @param deptOwn
     * @param validStates
     * @return
     */
    List<DawnOrgDept> queryDepartments(DeptOwnEnum deptOwn, List<DeptTypeEnum> deptTypes,
            List<ValidStateEnum> validStates);
}

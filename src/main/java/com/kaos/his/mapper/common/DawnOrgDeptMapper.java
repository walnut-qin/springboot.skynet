package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.DawnOrgDept;

public interface DawnOrgDeptMapper {
    /**
     * 主键查询
     * 
     * @param deptCode 科室编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnOrgDept queryDepartment(String deptCode);
}

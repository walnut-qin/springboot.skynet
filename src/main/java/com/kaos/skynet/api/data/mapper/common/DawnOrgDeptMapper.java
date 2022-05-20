package com.kaos.skynet.api.data.mapper.common;

import java.util.List;

import com.kaos.skynet.api.data.entity.common.DawnOrgDept;
import com.kaos.skynet.api.data.entity.common.DawnOrgDept.DeptTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

public interface DawnOrgDeptMapper {
    /**
     * 主键查询
     * 
     * @param deptCode 科室编码；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnOrgDept queryDept(String deptCode);

    /**
     * 查询科室列表
     * 
     * @param deptOwn
     * @param validStates
     * @return
     */
    List<DawnOrgDept> queryDepts(DeptOwnEnum deptOwn, List<DeptTypeEnum> deptTypes,
            List<ValidEnum> valids);
}

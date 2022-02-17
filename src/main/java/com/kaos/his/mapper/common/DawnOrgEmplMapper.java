package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.DawnOrgEmpl;

public interface DawnOrgEmplMapper {
    /**
     * 查询本院职工实体
     * 
     * @param emplCode 员工编码；值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnOrgEmpl queryEmployee(String emplCode);

    /**
     * 查询外院职工实体
     * 
     * @param emplCode 员工编码；值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnOrgEmpl queryOuterEmployee(String emplCode);
}

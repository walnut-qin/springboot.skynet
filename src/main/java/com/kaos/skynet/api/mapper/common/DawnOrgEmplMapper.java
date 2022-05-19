package com.kaos.skynet.api.mapper.common;

import java.util.List;

import com.kaos.skynet.api.entity.common.DawnOrgEmpl;

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

    /**
     * 查询所有有效员工
     * 
     * @return
     */
    List<DawnOrgEmpl> queryValidEmployees();
}

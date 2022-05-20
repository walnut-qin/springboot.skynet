package com.kaos.skynet.api.data.mapper.common;

import java.util.List;

import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

public interface DawnOrgEmplMapper {
    /**
     * 查询本院职工实体
     * 
     * @param emplCode 员工编码；值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    DawnOrgEmpl queryEmpl(String emplCode);

    /**
     * 查询员工列表
     * 
     * @param valids 有效性；值为 {@code null} 时，不作为判断条件
     * @return
     */
    List<DawnOrgEmpl> queryEmpls(SexEnum sex, List<ValidEnum> valids);
}

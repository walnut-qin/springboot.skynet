package com.kaos.skynet.api.data.mapper.common;

import java.util.List;

import com.kaos.skynet.api.data.entity.common.DawnOrgEmpl;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
    List<DawnOrgEmpl> queryEmpls(Key key);

    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 性别
         */
        private SexEnum sex;

        /**
         * 有效性列表
         */
        private List<ValidEnum> valids;
    }
}

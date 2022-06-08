package com.kaos.skynet.api.data.mapper.common;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.entity.common.DawnOrgDept;
import com.kaos.skynet.api.data.entity.common.DawnOrgDept.DeptTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
     * @param key
     * @return
     */
    List<DawnOrgDept> queryDepts(@NotNull(message = "查询键值不能为空") Key key);

    /**
     * 查询键值
     */
    @Getter
    @Setter
    @Builder
    public static class Key {
        /**
         * 院区
         */
        private DeptOwnEnum deptOwn;

        /**
         * 科室类型
         */
        private List<DeptTypeEnum> deptTypes;

        /**
         * 有效性标志
         */
        private List<ValidEnum> valids;
    }
}

package com.kaos.skynet.api.data.mapper.inpatient.surgery;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryArrangeRoleEnum;

import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Getter;

@Validated
public interface MetOpsArrangeMapper {
    /**
     * 查询列表
     * 
     * @param operationNo 手术编码；等于 {@code null} 时，不作为判断条件
     * @param roles       安排内容；等于 {@code null} 时，不作为判断条件
     * @return
     */
    List<MetOpsArrange> queryMetOpsArranges(@NotNull(message = "检索Key不能为空") Key key);

    /**
     * 检索key
     */
    @Getter
    @Builder
    public static class Key {
        /**
         * 手术申请号
         */
        private String operationNo;

        /**
         * 角色清单
         */
        private List<SurgeryArrangeRoleEnum> roles;
    }
}

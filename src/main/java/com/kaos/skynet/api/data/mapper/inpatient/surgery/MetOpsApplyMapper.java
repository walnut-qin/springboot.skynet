package com.kaos.skynet.api.data.mapper.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryStatusEnum;

import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Getter;

@Validated
public interface MetOpsApplyMapper {
    /**
     * 主键查询
     * 
     * @param operationNo 手术号；等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsApply queryApply(String operationNo);

    /**
     * 查询满足条件的手术申请记录
     * 
     * @param key
     * @return
     */
    List<MetOpsApply> queryApplies(@NotNull(message = "查询索引不能为空") Key key);

    @Getter
    @Builder
    public static class Key {
        /**
         * 住院号
         */
        private String patientNo;

        /**
         * 手术科室编码
         */
        private String execDeptCode;

        /**
         * 预约开始时间
         */
        private LocalDateTime beginPreDate;

        /**
         * 预约开始时间
         */
        private LocalDateTime endPreDate;

        /**
         * 手术状态
         */
        private List<SurgeryStatusEnum> execStatus;

        /**
         * 麻醉标识
         */
        private Boolean anesFlag;

        /**
         * 有效性标识
         */
        private ValidStateEnum valid;

        /**
         * 院区
         */
        private DeptOwnEnum deptOwn;
    }
}

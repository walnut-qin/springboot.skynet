package com.kaos.his.mapper.inpatient.surgery;

import java.util.List;

import com.kaos.his.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.his.enums.SurgeryArrangeRoleEnum;

public interface MetOpsArrangeMapper {
    /**
     * 查询列表
     * 
     * @param operationNo
     * @param roles
     * @return
     */
    List<MetOpsArrange> queryMetOpsArranges(String operationNo, List<SurgeryArrangeRoleEnum> roles);
}

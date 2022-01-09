package com.kaos.his.mapper.common;

import com.kaos.his.entity.common.Employee;
import com.kaos.his.enums.ValidStateEnum;

public interface EmployeeMapper {
    /**
     * 主键查询
     * 
     * @param emplCode 工号
     * @param valid    有效标识，若为null，则不纳入判断
     * @return
     */
    Employee queryEmployee(String emplCode, ValidStateEnum valid);

    /**
     * 查询院外员工（常常雇佣院外手术医生）
     * 
     * @param emplCode
     * @return
     */
    Employee queryInformalEmployee(String emplCode, ValidStateEnum valid);
}

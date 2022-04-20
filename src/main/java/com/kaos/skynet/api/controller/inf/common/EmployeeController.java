package com.kaos.skynet.api.controller.inf.common;

import java.time.LocalDateTime;
import java.util.List;

import com.kaos.skynet.enums.impl.common.EmplTypeEnum;

public interface EmployeeController {
    /**
     * 检索系统所有有效的职工信息
     * 
     * @return
     */
    public List<EmployeeInfo> queryValidEmployees();

    /**
     * 员工信息实体
     */
    public static class EmployeeInfo {
        /**
         * 工号
         */
        public String emplCode = null;

        /**
         * 员工姓名
         */
        public String emplName = null;

        /**
         * 拼写码
         */
        public String inputCode = null;

        /**
         * 员工分类
         */
        public EmplTypeEnum emplType = null;

        /**
         * 归属科室编码
         */
        public String deptCode = null;

        /**
         * 创建日期
         */
        public LocalDateTime createDate = null;
    }
}

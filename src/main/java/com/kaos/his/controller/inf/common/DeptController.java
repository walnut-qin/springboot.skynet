package com.kaos.his.controller.inf.common;

import javax.validation.constraints.NotNull;

import com.kaos.his.enums.impl.common.DeptOwnEnum;

public interface DeptController {
    /**
     * 获取科室信息
     * 
     * @param deptCode
     */
    QueryDeptInfoRsp queryDeptInfo(@NotNull(message = "科室编码不能为空") String deptCode);

    /**
     * 响应
     */
    public static class QueryDeptInfoRsp {
        /**
         * 科室编码
         */
        public String deptCode = null;

        /**
         * 科室名称
         */
        public String deptName = null;

        /**
         * 院区
         */
        public DeptOwnEnum deptOwn = null;
    }
}

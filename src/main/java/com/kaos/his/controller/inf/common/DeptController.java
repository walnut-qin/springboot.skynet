package com.kaos.his.controller.inf.common;

import java.util.List;

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

    /**
     * 查询科室列表
     * 
     * @param deptOwn
     * @return
     */
    QueryDeptListRsp queryDeptList(@NotNull(message = "院区不能为空") DeptOwnEnum deptOwn);

    public static class QueryDeptListRsp {
        /**
         * 数量
         */
        public Integer size = null;

        /**
         * 明细
         */
        public List<QueryDeptInfoRsp> deptCodes = null;
    }
}

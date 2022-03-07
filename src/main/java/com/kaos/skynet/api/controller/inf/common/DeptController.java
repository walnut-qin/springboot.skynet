package com.kaos.skynet.api.controller.inf.common;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.enums.impl.common.DeptOwnEnum;
import com.kaos.skynet.enums.impl.common.DeptTypeEnum;

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
    QueryDeptListRsp queryDeptList(@Valid QueryDeptListReq req);

    public static class QueryDeptListReq {
        /**
         * 数量
         */
        @NotNull(message = "院区不能为空")
        public DeptOwnEnum deptOwn = null;

        /**
         * 明细
         */
        public List<DeptTypeEnum> deptTypes = null;
    }

    public static class QueryDeptListRsp {
        /**
         * 数量
         */
        public Integer size = null;

        /**
         * 明细
         */
        public List<QueryDeptInfoRsp> depts = null;
    }
}

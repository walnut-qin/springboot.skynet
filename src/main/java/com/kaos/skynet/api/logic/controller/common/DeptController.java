package com.kaos.skynet.api.logic.controller.common;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.entity.common.DawnOrgDept.DeptTypeEnum;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.api.data.mapper.common.DawnOrgDeptMapper;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "api/common/dept", "/ms/common/dept" })
public class DeptController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 科室缓存
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    /**
     * 科室接口
     */
    @Autowired
    DawnOrgDeptMapper deptMapper;

    /**
     * 查询科室信息
     * 
     * @param deptCode
     * @return
     */
    @RequestMapping(value = "queryDeptInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public QueryDeptInfoRsp queryDeptInfo(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 日志
        log.info(String.format("查询科室信息(deptCode = %s)", deptCode));

        // 调用业务
        var dept = deptCache.get(deptCode);
        if (dept == null) {
            return null;
        }

        // 构造响应
        var rspBuilder = QueryDeptInfoRsp.builder();
        rspBuilder.deptCode(dept.getDeptCode());
        rspBuilder.deptName(dept.getDeptName());
        rspBuilder.deptOwn(dept.getDeptOwn());
        return rspBuilder.build();
    }

    /**
     * 响应
     */
    @Builder
    public static class QueryDeptInfoRsp {
        /**
         * 科室编码
         */
        public String deptCode;

        /**
         * 科室名称
         */
        public String deptName;

        /**
         * 院区
         */
        public DeptOwnEnum deptOwn;
    }

    /**
     * 查询科室列表
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "queryDeptList", method = RequestMethod.POST, produces = MediaType.JSON)
    public QueryDeptListRsp queryDeptList(@RequestBody @Valid QueryDeptListReq req) {
        // 日志
        log.info(String.format("查询科室清单(req = %s)", json.toJson(req)));

        // 查询科室信息
        var keyBuilder = DawnOrgDeptMapper.Key.builder();
        keyBuilder.deptOwn(req.getDeptOwn());
        keyBuilder.deptTypes(req.getDeptTypes());
        keyBuilder.valids(Lists.newArrayList(ValidEnum.VALID));
        var depts = deptMapper.queryDepts(null);

        // 构造响应
        var rspBuilder = QueryDeptListRsp.builder();
        rspBuilder.size(depts.size());
        rspBuilder.depts(depts.stream().map(x -> {
            var itemBuilder = QueryDeptInfoRsp.builder();
            itemBuilder.deptCode(x.getDeptCode());
            itemBuilder.deptName(x.getDeptName());
            itemBuilder.deptOwn(x.getDeptOwn());
            return itemBuilder.build();
        }).toList());

        return rspBuilder.build();
    }

    @Getter
    public static class QueryDeptListReq {
        /**
         * 数量
         */
        @NotNull(message = "院区不能为空")
        private DeptOwnEnum deptOwn;

        /**
         * 明细
         */
        private List<DeptTypeEnum> deptTypes;
    }

    @Getter
    @Builder
    public static class QueryDeptListRsp {
        /**
         * 数量
         */
        private Integer size;

        /**
         * 明细
         */
        private List<QueryDeptInfoRsp> depts;
    }
}

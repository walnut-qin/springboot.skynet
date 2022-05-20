package com.kaos.skynet.api.controller.impl.common;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.common.DeptController;
import com.kaos.skynet.api.data.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.enums.ValidEnum;
import com.kaos.skynet.api.data.mapper.common.DawnOrgDeptMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/dept")
public class DeptControllerImpl implements DeptController {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(DeptControllerImpl.class);

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

    @Override
    @RequestMapping(value = "queryDeptInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public QueryDeptInfoRsp queryDeptInfo(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 调用业务
        var dept = this.deptCache.get(deptCode);
        if (dept == null) {
            return null;
        }

        // 构造响应
        QueryDeptInfoRsp rsp = new QueryDeptInfoRsp();
        rsp.deptCode = dept.getDeptCode();
        rsp.deptName = dept.getDeptName();
        rsp.deptOwn = dept.getDeptOwn();
        return rsp;
    }

    @Override
    @RequestMapping(value = "queryDeptList", method = RequestMethod.POST, produces = MediaType.JSON)
    public QueryDeptListRsp queryDeptList(@RequestBody @Valid QueryDeptListReq req) {
        // 查询科室信息
        var depts = this.deptMapper.queryDepts(req.deptOwn, req.deptTypes, Lists.newArrayList(ValidEnum.VALID));

        // 构造响应
        QueryDeptListRsp rsp = new QueryDeptListRsp();
        rsp.size = depts.size();
        rsp.depts = depts.stream().map(x -> {
            QueryDeptInfoRsp rspItem = new QueryDeptInfoRsp();
            rspItem.deptCode = x.getDeptCode();
            rspItem.deptName = x.getDeptName();
            rspItem.deptOwn = x.getDeptOwn();
            return rspItem;
        }).toList();

        return rsp;
    }
}

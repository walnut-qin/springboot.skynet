package com.kaos.his.controller.impl.common;

import javax.validation.constraints.NotNull;

import com.kaos.his.cache.impl.common.DawnOrgDeptCache;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.common.DeptController;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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

    @Override
    @RequestMapping(value = "queryDeptInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public QueryDeptInfoRsp queryDeptInfo(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 调用业务
        var dept = this.deptCache.getValue(deptCode);
        if (dept == null) {
            return null;
        }

        // 构造响应
        QueryDeptInfoRsp rsp = new QueryDeptInfoRsp();
        rsp.deptCode = dept.deptCode;
        rsp.deptName = dept.deptName;
        rsp.deptOwn = dept.deptOwn;
        return rsp;
    }
}

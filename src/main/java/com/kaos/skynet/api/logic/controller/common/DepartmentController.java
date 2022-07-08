package com.kaos.skynet.api.logic.controller.common;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.data.his.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@CrossOrigin
@Validated
@RestController
@RequestMapping("/api/common/department")
public class DepartmentController {
    /**
     * 实体信息服务
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ApiName("获取科室信息")
    @RequestMapping(value = "getDeptInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    GetDeptInfo.RspBody getDeptInfo(@RequestBody @Valid GetDeptInfo.ReqBody reqBody) {
        // 调用服务
        var dept = deptCache.get(reqBody.deptCode);
        if (dept == null) {
            throw new RuntimeException("科室不存在!");
        }

        // 构造响应体
        var rspBuilder = GetDeptInfo.RspBody.builder();
        rspBuilder.deptCode(dept.getDeptCode());
        rspBuilder.deptName(dept.getDeptName());
        rspBuilder.deptOwn(dept.getDeptOwn());

        return rspBuilder.build();
    }

    static class GetDeptInfo {
        static class ReqBody {
            /**
             * 患者卡号
             */
            @NotBlank(message = "科室编码不能为空")
            String deptCode;
        }

        @Builder
        static class RspBody {
            /**
             * 科室编码
             */
            String deptCode;

            /**
             * 科室名称
             */
            String deptName;

            /**
             * 院区
             */
            DeptOwnEnum deptOwn;
        }
    }
}

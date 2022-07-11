package com.kaos.skynet.api.logic.controller.common;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.api.data.his.mapper.common.DawnOrgDeptMapper;
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
     * 科室接口
     */
    @Autowired
    DawnOrgDeptMapper deptMapper;

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

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ApiName("获取科室列表信息")
    @RequestMapping(value = "getDeptsInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<GetDeptsInfo.RspBody> getDeptsInfo(@RequestBody @Valid GetDeptsInfo.ReqBody reqBody) {
        // 调用服务
        var keyBuilder = DawnOrgDeptMapper.Key.builder();
        keyBuilder.deptOwn(reqBody.deptOwn);
        keyBuilder.valids(Lists.newArrayList(ValidEnum.VALID));
        var depts = deptMapper.queryDepts(keyBuilder.build());

        // 构造响应体
        var rspBuilder = GetDeptsInfo.RspBody.builder();
        return depts.stream().map(x -> {
            rspBuilder.deptCode(x.getDeptCode());
            rspBuilder.deptName(x.getDeptName());
            rspBuilder.deptOwn(x.getDeptOwn());
            return rspBuilder.build();
        }).toList();
    }

    static class GetDeptsInfo {
        static class ReqBody {
            /**
             * 患者卡号
             */
            DeptOwnEnum deptOwn;
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

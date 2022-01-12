package com.kaos.his.controller.outpatient.gcp;

import javax.validation.constraints.NotBlank;

import com.kaos.his.service.GcpService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/outpatient/gcp")
public class GcpController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(GcpController.class.getName());

    /**
     * 实体信息服务
     */
    @Autowired
    GcpService gcpService;

    @ResponseBody
    @RequestMapping(value = "checkGcpPrivilege", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String checkGcpPrivilege(@NotBlank(message = "科室编码不能为空") String deptCode,
            @NotBlank(message = "门诊号不能为空") String clinicCode) {
        // 记录日志
        this.logger.info(String.format("检查GCP权限(deptCode = %s, clinicCode = %s)", deptCode, clinicCode));

        // 调用服务
        return this.gcpService.checkGcpPrivilege(deptCode, clinicCode) ? "1" : "0";
    }
}

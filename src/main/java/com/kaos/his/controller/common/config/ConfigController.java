package com.kaos.his.controller.common.config;

import javax.validation.constraints.NotBlank;

import com.kaos.his.service.common.ConfigService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/config")
public class ConfigController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ConfigController.class);

    /**
     * 实体信息服务
     */
    @Autowired
    ConfigService configService;

    /**
     * 检索开关变量的值
     */
    @RequestMapping(value = "querySwitchState", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String querySwitchState(@NotBlank(message = "开关名称不能为空") String key) {
        // 记录日志
        this.logger.info(String.format("查询开关变量(key = %s)", key));

        // 调用服务
        return this.configService.querySwitchValue(key).toString();
    }
}

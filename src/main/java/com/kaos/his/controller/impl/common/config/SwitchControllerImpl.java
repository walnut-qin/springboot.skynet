package com.kaos.his.controller.impl.common.config;

import javax.validation.constraints.NotBlank;

import com.kaos.his.controller.inf.common.config.SwitchController;
import com.kaos.his.service.inf.common.config.SwitchService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/config/switch")
public class SwitchControllerImpl implements SwitchController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(SwitchControllerImpl.class);

    /**
     * 实体信息服务
     */
    @Autowired
    SwitchService switchService;

    /**
     * 检索开关变量的值
     */
    @Override
    @RequestMapping(value = "queryState", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public Boolean queryState(@NotBlank(message = "开关名不能为空") String switchName) {
        // 记录日志
        this.logger.info(String.format("查询开关变量(key = %s)", switchName));

        // 调用服务
        return this.switchService.querySwitchValue(switchName);
    }
}

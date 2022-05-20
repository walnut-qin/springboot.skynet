package com.kaos.skynet.api.controller.impl.common.config;

import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.common.config.SwitchController;
import com.kaos.skynet.api.data.cache.common.config.ConfigSwitchCache;
import com.kaos.skynet.api.data.enums.ValidEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping({ "/ms/common/config/switch", "/ms/common/config" })
public class SwitchControllerImpl implements SwitchController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(SwitchControllerImpl.class);

    @Autowired
    ConfigSwitchCache configSwitchCache;

    /**
     * 检索开关变量的值
     */
    @Override
    @RequestMapping(value = { "queryState", "querySwitchState" }, method = RequestMethod.GET, produces = MediaType.TEXT)
    public Boolean queryState(@NotBlank(message = "开关名不能为空") String switchName) {
        // 记录日志
        this.logger.info(String.format("查询开关变量(key = %s)", switchName));

        // 获取开关的值
        var swt = this.configSwitchCache.get(switchName);
        if (swt == null || swt.getValid() != ValidEnum.VALID) {
            return false;
        }

        return swt.getValue();
    }
}

package com.kaos.his.controller.impl.common.config;

import javax.validation.constraints.NotBlank;

import com.kaos.his.cache.Cache;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.common.config.SwitchController;
import com.kaos.his.entity.common.config.ConfigSwitch;
import com.kaos.his.enums.impl.common.ValidStateEnum;

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
    Cache<String, ConfigSwitch> switchCache;

    /**
     * 检索开关变量的值
     */
    @Override
    @RequestMapping(value = { "queryState", "querySwitchState" }, method = RequestMethod.GET, produces = MediaType.TEXT)
    public Boolean queryState(@NotBlank(message = "开关名不能为空") String switchName) {
        // 记录日志
        this.logger.info(String.format("查询开关变量(key = %s)", switchName));

        // 获取开关的值
        var swt = this.switchCache.getValue(switchName);
        if (swt == null || swt.valid != ValidStateEnum.有效) {
            return false;
        }

        return swt.value;
    }
}

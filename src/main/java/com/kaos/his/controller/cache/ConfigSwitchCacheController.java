package com.kaos.his.controller.cache;

import java.util.concurrent.ConcurrentMap;

import com.kaos.his.cache.common.config.ConfigSwitchCache;
import com.kaos.his.entity.common.config.ConfigSwitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/ConfigSwitch")
public class ConfigSwitchCacheController {
    /**
     * 实体信息服务
     */
    @Autowired
    ConfigSwitchCache switchCache;

    /**
     * 检索开关变量的值
     */
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ConcurrentMap<String, ConfigSwitch> show() {
        return this.switchCache.show();
    }
}

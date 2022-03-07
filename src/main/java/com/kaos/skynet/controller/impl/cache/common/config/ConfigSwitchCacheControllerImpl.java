package com.kaos.skynet.controller.impl.cache.common.config;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.cache.Cache.View;
import com.kaos.skynet.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.common.config.ConfigSwitch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/config/switch")
public class ConfigSwitchCacheControllerImpl implements CacheController<String, ConfigSwitch> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, ConfigSwitch> switchCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View<String, ?> show() {
        return this.switchCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refresh(@NotNull(message = "键值不能为空") String key) {
        this.switchCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refreshAll() {
        this.switchCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.switchCache.invalidateAll();
        return "清空缓存成功";
    }
}

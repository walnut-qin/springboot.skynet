package com.kaos.skynet.controller.impl.cache.common.config.multi;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.cache.Cache.View;
import com.kaos.skynet.controller.MediaType;
import com.kaos.skynet.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.common.config.ConfigMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/config/multi/map")
public class ConfigMultiMapCacheControllerImpl implements CacheController<String, Cache<String, ConfigMap>> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, Cache<String, ConfigMap>> multiMapCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = MediaType.JSON)
    public View<String, ?> show() {
        return this.multiMapCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String refresh(@NotNull(message = "键值不能为空") String key) {
        this.multiMapCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String refreshAll() {
        this.multiMapCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.multiMapCache.invalidateAll();
        return "清空缓存成功";
    }
}

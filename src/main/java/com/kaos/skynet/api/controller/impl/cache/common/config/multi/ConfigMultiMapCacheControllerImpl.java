package com.kaos.skynet.api.controller.impl.cache.common.config.multi;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
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
    public View show() {
        return this.multiMapCache.show();
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.multiMapCache.invalidateAll();
        return "清空缓存成功";
    }
}

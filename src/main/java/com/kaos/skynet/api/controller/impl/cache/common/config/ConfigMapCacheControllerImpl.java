package com.kaos.skynet.api.controller.impl.cache.common.config;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.common.config.ConfigMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/config/map")
public class ConfigMapCacheControllerImpl implements CacheController<String, ConfigMap> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, ConfigMap> mapCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View show() {
        return this.mapCache.show();
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.mapCache.invalidateAll();
        return "清空缓存成功";
    }
}

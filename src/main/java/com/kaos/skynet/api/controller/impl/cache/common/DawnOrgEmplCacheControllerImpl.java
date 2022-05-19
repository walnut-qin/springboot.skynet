package com.kaos.skynet.api.controller.impl.cache.common;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.api.entity.common.DawnOrgEmpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/empl")
public class DawnOrgEmplCacheControllerImpl implements CacheController<String, DawnOrgEmpl> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, DawnOrgEmpl> emplCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View show() {
        return this.emplCache.show();
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.emplCache.invalidateAll();
        return "清空缓存成功";
    }
}

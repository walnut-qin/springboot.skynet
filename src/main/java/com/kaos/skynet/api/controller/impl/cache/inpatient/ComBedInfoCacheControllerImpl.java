package com.kaos.skynet.api.controller.impl.cache.inpatient;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.api.entity.inpatient.ComBedInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/inpatient/bed")
public class ComBedInfoCacheControllerImpl implements CacheController<String, ComBedInfo> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, ComBedInfo> bedInfoCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View show() {
        return this.bedInfoCache.show();
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.bedInfoCache.invalidateAll();
        return "清空缓存成功";
    }
}

package com.kaos.skynet.api.controller.impl.cache.pipe.lis;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.pipe.lis.LisResultNew;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/pipe/lis/covid")
public class LisResultNewCacheControllerImpl implements CacheController<String, LisResultNew> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, LisResultNew> covidCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = MediaType.JSON)
    public View<String, ?> show() {
        return this.covidCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String refresh(@NotNull(message = "键值不能为空") String key) {
        this.covidCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String refreshAll() {
        this.covidCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String clear() {
        this.covidCache.invalidateAll();
        return "清空缓存成功";
    }
}

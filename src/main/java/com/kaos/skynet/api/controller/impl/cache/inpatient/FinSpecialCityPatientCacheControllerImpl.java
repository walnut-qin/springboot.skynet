package com.kaos.skynet.api.controller.impl.cache.inpatient;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.inpatient.FinSpecialCityPatient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/inpatient/special")
public class FinSpecialCityPatientCacheControllerImpl implements CacheController<String, FinSpecialCityPatient> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<String, FinSpecialCityPatient> specialCityPatientCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = MediaType.JSON)
    public View<String, ?> show() {
        return this.specialCityPatientCache.show();
    }

    @Override
    @RequestMapping(value = "refresh", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String refresh(@NotNull(message = "键值不能为空") String key) {
        this.specialCityPatientCache.refresh(key);
        return String.format("更新缓存%s成功", key);
    }

    @Override
    @RequestMapping(value = "refreshAll", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String refreshAll() {
        this.specialCityPatientCache.refreshAll();
        return "更新缓存成功";
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String clear() {
        this.specialCityPatientCache.invalidateAll();
        return "清空缓存成功";
    }
}

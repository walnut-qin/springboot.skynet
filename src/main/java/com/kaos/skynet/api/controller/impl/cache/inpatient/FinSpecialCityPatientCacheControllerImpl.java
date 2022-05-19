package com.kaos.skynet.api.controller.impl.cache.inpatient;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.api.entity.inpatient.FinSpecialCityPatient;

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
    public View show() {
        return this.specialCityPatientCache.show();
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String clear() {
        this.specialCityPatientCache.invalidateAll();
        return "清空缓存成功";
    }
}

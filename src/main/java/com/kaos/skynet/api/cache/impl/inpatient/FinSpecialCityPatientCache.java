package com.kaos.skynet.api.cache.impl.inpatient;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.inpatient.FinSpecialCityPatientMapper;
import com.kaos.skynet.entity.inpatient.FinSpecialCityPatient;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinSpecialCityPatientCache implements Cache<String, FinSpecialCityPatient> {
    /**
     * 数据库接口
     */
    @Autowired
    FinSpecialCityPatientMapper specialCityPatientMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinSpecialCityPatientCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<FinSpecialCityPatient>> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<FinSpecialCityPatient>>() {
                @Override
                public Optional<FinSpecialCityPatient> load(String key) throws Exception {
                    var ref = FinSpecialCityPatientCache.this.specialCityPatientMapper.querySpecialCityPatient(key);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public FinSpecialCityPatient getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key).orNull();
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.refresh(key);
        }
    }

    @Override
    public View<String, Optional<FinSpecialCityPatient>> show() {
        View<String, Optional<FinSpecialCityPatient>> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}
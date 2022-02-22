package com.kaos.his.cache.inpatient;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 住院主表cache，若需要实时数据，需要在刷新后再获取
 */
@Component
public class FinIprInMainInfoCache implements ICache<String, FinIprInMainInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinIprInMainInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, FinIprInMainInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .recordStats()
            .build(new CacheLoader<String, FinIprInMainInfo>() {
                @Override
                public FinIprInMainInfo load(String key) throws Exception {
                    return FinIprInMainInfoCache.this.inMainInfoMapper.queryInMainInfo(key);
                };
            });

    @Override
    public FinIprInMainInfo getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key);
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public View<String, FinIprInMainInfo> show() {
        View<String, FinIprInMainInfo> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

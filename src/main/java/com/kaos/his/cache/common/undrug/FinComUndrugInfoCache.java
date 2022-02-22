package com.kaos.his.cache.common.undrug;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.undrug.FinComUndrugInfo;
import com.kaos.his.mapper.common.undrug.FinComUndrugInfoMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 非药品项目编码 -> 项目信息
 * @param 容量 100
 * @param 刷频 1次/1天
 * @param 过期 永不
 */
@Component
public class FinComUndrugInfoCache implements ICache<String, FinComUndrugInfo> {
    /**
     * 数据库接口
     */
    @Autowired
    FinComUndrugInfoMapper undrugInfoMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinComUndrugInfoCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, FinComUndrugInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .recordStats()
            .build(new CacheLoader<String, FinComUndrugInfo>() {
                @Override
                public FinComUndrugInfo load(String key) throws Exception {
                    return FinComUndrugInfoCache.this.undrugInfoMapper.queryUndrugInfo(key);
                };
            });

    @Override
    public FinComUndrugInfo getValue(String key) {
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
    public View<String, FinComUndrugInfo> show() {
        View<String, FinComUndrugInfo> view = new View<>();
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

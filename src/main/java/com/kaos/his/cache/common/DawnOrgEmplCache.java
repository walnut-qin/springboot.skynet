package com.kaos.his.cache.common;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.mapper.common.DawnOrgEmplMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 职工编码 -> 职工信息
 * @param 容量 1000
 * @param 刷频 1次/1天
 * @param 过期 永不
 */
@Component
public class DawnOrgEmplCache implements ICache<String, DawnOrgEmpl> {
    /**
     * 数据库接口
     */
    @Autowired
    DawnOrgEmplMapper dawnOrgEmplMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DawnOrgDeptCache.class.getName());

    /**
     * Loading cache
     */
    LoadingCache<String, DawnOrgEmpl> cache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .recordStats()
            .build(new CacheLoader<String, DawnOrgEmpl>() {
                @Override
                public DawnOrgEmpl load(String key) throws Exception {
                    var empl = DawnOrgEmplCache.this.dawnOrgEmplMapper.queryEmployee(key);
                    if (empl == null) {
                        empl = DawnOrgEmplCache.this.dawnOrgEmplMapper.queryOuterEmployee(key);
                    }
                    return empl;
                };
            });

    @Override
    public DawnOrgEmpl getValue(String key) {
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
    public View<String, DawnOrgEmpl> show() {
        View<String, DawnOrgEmpl> view = new View<>();
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

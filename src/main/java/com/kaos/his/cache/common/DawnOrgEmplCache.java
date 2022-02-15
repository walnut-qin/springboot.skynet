package com.kaos.his.cache.common;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.DawnOrgEmpl;
import com.kaos.his.mapper.common.DawnOrgEmplMapper;
import com.kaos.inf.ICache;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class DawnOrgEmplCache implements ICache<DawnOrgEmpl> {
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
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, DawnOrgEmpl>() {
                @Override
                public DawnOrgEmpl load(String key) throws Exception {
                    return DawnOrgEmplCache.this.dawnOrgEmplMapper.queryEmployee(key);
                };
            });

    /**
     * 禁止实例化
     * 
     * @throws ExecutorException
     */
    private DawnOrgEmplCache() throws ExecutorException {
    }

    @Override
    public DawnOrgEmpl getValue(String key) {
        try {
            return this.cache.get(key);
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public ConcurrentMap<String, DawnOrgEmpl> show() {
        return this.cache.asMap();
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }

    /**
     * 静态内部类
     */
    static class InnerDawnOrgEmplCache {
        static DawnOrgEmplCache dawnOrgEmplCache = new DawnOrgEmplCache();
    }

    /**
     * 获取单例
     * 
     * @return
     */
    public static DawnOrgEmplCache getInstance() {
        return InnerDawnOrgEmplCache.dawnOrgEmplCache;
    }
}

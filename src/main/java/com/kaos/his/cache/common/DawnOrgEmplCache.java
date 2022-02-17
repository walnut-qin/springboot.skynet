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
import org.springframework.stereotype.Component;

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
            .maximumSize(300)
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
}

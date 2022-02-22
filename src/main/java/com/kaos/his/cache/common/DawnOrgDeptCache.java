package com.kaos.his.cache.common;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.mapper.common.DawnOrgDeptMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 科室编码 -> 科室信息
 * @param 容量 100
 * @param 刷频 1次/1天
 * @param 过期 永不
 */
@Component
public class DawnOrgDeptCache implements ICache<String, DawnOrgDept> {
    /**
     * 数据库接口
     */
    @Autowired
    DawnOrgDeptMapper dawnOrgDeptMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DawnOrgDeptCache.class.getName());

    /**
     * Loading cache
     */
    LoadingCache<String, DawnOrgDept> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .recordStats()
            .build(new CacheLoader<String, DawnOrgDept>() {
                @Override
                public DawnOrgDept load(String key) throws Exception {
                    return DawnOrgDeptCache.this.dawnOrgDeptMapper.queryDepartment(key);
                };
            });

    @Override
    public DawnOrgDept getValue(String key) {
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
    public View<String, DawnOrgDept> show() {
        View<String, DawnOrgDept> view = new View<>();
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

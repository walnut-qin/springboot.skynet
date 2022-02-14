package com.kaos.his.cache.common;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.mapper.common.DawnOrgDeptMapper;
import com.kaos.inf.ICache;

import org.apache.ibatis.executor.ExecutorException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 科室字典，容量 = 300，不过期，刷新评率 = 1次/天
 */
public class DepartmentCache implements ICache<DawnOrgDept> {
    /**
     * 数据库接口
     */
    @Autowired
    DawnOrgDeptMapper departmentMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DepartmentCache.class.getName());

    /**
     * Loading cache
     */
    LoadingCache<String, DawnOrgDept> cache = CacheBuilder.newBuilder()
            .maximumSize(300)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, DawnOrgDept>() {
                @Override
                public DawnOrgDept load(String key) throws Exception {
                    return DepartmentCache.this.departmentMapper.queryDepartment(key);
                };
            });

    /**
     * 禁止实例化
     * 
     * @throws ExecutorException
     */
    private DepartmentCache() throws ExecutorException {
    }

    @Override
    public DawnOrgDept getValue(String key) {
        try {
            return this.cache.get(key);
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public ConcurrentMap<String, DawnOrgDept> show() {
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
    static class InnerDepartmentCache {
        static DepartmentCache departmentCache = new DepartmentCache();
    }

    /**
     * 获取单例
     * 
     * @return
     */
    public static DepartmentCache getInstance() {
        return InnerDepartmentCache.departmentCache;
    }
}

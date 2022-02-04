package com.kaos.his.cache.common;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.Department;
import com.kaos.his.mapper.common.DepartmentMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 科室字典
 */
public class DepartmentCache {
    /**
     * 数据库接口
     */
    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DepartmentCache.class.getName());

    /**
     * Loading cache
     */
    LoadingCache<String, Department> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(1, TimeUnit.HOURS)
            .build(new CacheLoader<String, Department>() {
                @Override
                public Department load(String key) throws Exception {
                    return DepartmentCache.this.departmentMapper.queryDepartment(key);
                };
            });

    /**
     * 查询科室实体
     * 
     * @param key
     * @return
     */
    public Department queryDepartment(String key) {
        try {
            return this.cache.get(key);
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    /**
     * 刷新cache
     * 
     * @param key
     */
    public void refreshItem(String key) {
        this.cache.refresh(key);
    }
}

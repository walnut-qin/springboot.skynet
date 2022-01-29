package com.kaos.helper.holiday.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.helper.holiday.HolidayHelper;
import com.kaos.helper.holiday.entity.*;
import com.kaos.helper.lock.LockHelper;
import com.kaos.helper.lock.impl.LockHelperImpl;

import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HolidayHelperImpl implements HolidayHelper {
    /**
     * 日志工具
     */
    Logger logger = Logger.getLogger(HolidayHelperImpl.class.getName());

    /**
     * HTTP句柄
     */
    RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
        {
            add(new GsonHttpMessageConverter(new GsonHelperImpl("yyyy-MM-dd").getGson()));
        }
    });

    /**
     * 本地缓存
     */
    static final Cache<String, DayInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    /**
     * 节点更新锁，确保每个节点不会同时被多个线程更新
     */
    static final LockHelper lockHelper = new LockHelperImpl("holidayCacheLock", 20);

    @Override
    public DayInfo getDayInfo(Date date) {
        // 初始日志
        this.logger.info(String.format("获取节假日信息", date.toString()));

        // 初始化时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 获取索引
        var key = formatter.format(date);

        // 尝试从cache获取
        var node = cache.getIfPresent(key);
        if (node != null) {
            this.logger.info("命中cache");
            return node;
        }

        // 未命中cache，从网络侧获取
        this.logger.info("未命中cache");
        var lock = lockHelper.mapToLock(key);
        try {
            this.logger.info(String.format("加锁中(%s)", lock.hashCode()));
            synchronized (lock) {
                this.logger.info(String.format("加锁完成(%s)", lock.hashCode()));
                // Double-Check
                node = cache.getIfPresent(key);
                if (node != null) {
                    this.logger.info("Double-Check, 命中cache, 节点已被其他线程更新");
                    return node;
                }

                // 网络调用
                var url = String.format("http://timor.tech/api/holiday/info/%s", key);
                var dayInfo = this.restTemplate.getForObject(url, DayInfo.class);
                this.logger.info("网络调用成功");

                // 更新cache
                cache.put(key, dayInfo);
                this.logger.info("将新节点加入cache");

                return dayInfo;
            }
        } catch (Exception ex) {
            this.logger.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        } finally {
            this.logger.info(String.format("解锁(%s)", lock.hashCode()));
        }
    }

    @Override
    public ConcurrentMap<String, DayInfo> getCache() {
        return cache.asMap();
    }
}

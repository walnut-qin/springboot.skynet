package com.kaos.helper.holiday.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.helper.holiday.HolidayHelper;
import com.kaos.helper.holiday.entity.*;

import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HolidayHelperImpl implements HolidayHelper {
    /**
     * 日志工具
     */
    static final Logger logger = Logger.getLogger(HolidayHelperImpl.class.getName());

    /**
     * 线程安全的HTTP服务
     */
    static final RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
        {
            add(new GsonHttpMessageConverter(new GsonHelperImpl("yyyy-MM-dd").getGson()));
        }
    });

    /**
     * 线程安全的Guava Cache
     */
    static final LoadingCache<String, DayInfo> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build(new CacheLoader<String, DayInfo>() {
                @Override
                public DayInfo load(String key) throws Exception {
                    var url = String.format("http://timor.tech/api/holiday/info/%s", key);
                    logger.info(String.format("从网络侧获取节假日信息(url = %s)", url));
                    return restTemplate.getForObject(url, DayInfo.class);
                };
            });

    @Override
    public DayInfo getDayInfo(Date date) {
        // 初始日志
        logger.info(String.format("获取节假日信息", date.toString()));

        // 初始化时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 获取索引
        var key = formatter.format(date);

        try {
            return cache.get(key);
        } catch (Exception e) {
            logger.warn(String.format("从LoadingCache获取 key = %s 的值失败，响应null，err = %s", key, e.getMessage()));
            return null;
        }
    }

    @Override
    public ConcurrentMap<String, DayInfo> getCache() {
        return cache.asMap();
    }
}

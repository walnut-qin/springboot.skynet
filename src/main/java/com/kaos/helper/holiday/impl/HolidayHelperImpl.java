package com.kaos.helper.holiday.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import com.kaos.helper.gson.GsonHelper;
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
     * GsonHelper实体
     */
    GsonHelper gsonHelper = new GsonHelperImpl("yyyy-MM-dd");

    /**
     * 用线程安全的容器作为cache，存储本地数据，原则上cache节点有效时间为24小时
     */
    static final ConcurrentHashMap<String, CacheNode> holidayCache = new ConcurrentHashMap<>();

    /**
     * cache锁，保证
     */
    static final LockHelper cacheLock = new LockHelperImpl("holidayCacheLock", 20);

    /**
     * HTTP句柄
     */
    RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
        {
            add(new GsonHttpMessageConverter(HolidayHelperImpl.this.gsonHelper.getGson()));
        }
    });

    /**
     * 获取节假日信息
     */
    @Override
    public DayInfo getDayInfo(Date date) {
        // 初始日志
        this.logger.info(String.format("获取节假日信息", date.toString()));

        // 初始化时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 获取索引
        var key = formatter.format(date);

        // 若cache节点有效，则无需网络调用
        if (holidayCache.containsKey(key)) {
            var node = holidayCache.get(key);
            if (node.validDate.after(new Date())) {
                this.logger.info("命中有效cache节点");
                return node.dayInfo;
            }
        }

        // 无有效cache节点
        this.logger.info("cache节点失效，重新获取");
        try {
            this.logger.info("加锁(holidayCacheLock)");
            synchronized (cacheLock.mapToLock(key)) {
                // Double-Check，防止多个线程重复更新
                if (holidayCache.containsKey(key)) {
                    var node = holidayCache.get(key);
                    if (node.validDate.after(new Date())) {
                        this.logger.info("cache节点已被其他线程更新");
                        return node.dayInfo;
                    }
                }

                // 构造请求url
                String url = String.format("http://timor.tech/api/holiday/info/%s", key);

                // 发送web请求
                var dayInfo = this.restTemplate.getForObject(url, DayInfo.class);
                this.logger.info(String.format("网络响应实体 = %s", this.gsonHelper.toJson(dayInfo)));

                // 如果存在旧节点，更新旧节点，否则插入新节点
                Calendar validDate = Calendar.getInstance();
                validDate.add(Calendar.DATE, 1);
                if (holidayCache.containsKey(key)) {
                    var node = holidayCache.get(key);
                    node.dayInfo = dayInfo;
                    node.validDate = validDate.getTime();
                    this.logger.info(String.format("更新旧cache节点(validDate -> %s)", node.validDate.toString()));
                } else {
                    var node = new CacheNode();
                    node.dayInfo = dayInfo;
                    node.validDate = validDate.getTime();
                    holidayCache.put(key, node);
                    this.logger.info(String.format("插入新cache节点(validDate -> %s)", node.validDate.toString()));
                }

                return dayInfo;
            }
        } finally {
            this.logger.info("解锁(holidayCacheLock)");
        }
    }
}

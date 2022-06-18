package com.kaos.skynet.plugin.timor;

import java.time.Duration;
import java.time.LocalDate;

import com.google.common.cache.CacheStats;
import com.google.common.collect.Lists;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.http.handler.AbstractHttpHandler;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.converter.LocalDateToStringConverter;
import com.kaos.skynet.plugin.timor.entity.DayInfo;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TimorPlugin {
    /**
     * 服务器IP
     */
    final static String ip = "timor.tech";

    /**
     * 服务器PORT
     */
    final static Integer port = 80;

    /**
     * Http句柄
     */
    AbstractHttpHandler httpHandler;

    /**
     * 时间格式转换器
     */
    final LocalDateToStringConverter dateToStringConverter = new LocalDateToStringConverter("yyyy-MM-dd");

    /**
     * 定制化缓存
     * 刷频：7天
     * 过期：30天
     */
    final Cache<LocalDate, DayInfo> infoCache = new Cache<>(365, new Converter<LocalDate, DayInfo>() {
        @Override
        public DayInfo convert(LocalDate key) {
            // 构造请求url
            String url = "api/holiday/info/{date}";
            // 从web端获取响应
            return httpHandler.getForObject(url, DayInfo.class, dateToStringConverter.convert(key));
        };
    }, Duration.ofDays(7), Duration.ofDays(30)) {
    };

    /**
     * 构造函数
     * 
     * @param jsonHttpMessageConverter
     */
    TimorPlugin(JsonHttpMessageConverter jsonHttpMessageConverter) {
        // 构造HTTP处理器
        httpHandler = new AbstractHttpHandler(
                new RestTemplate(Lists.newArrayList(jsonHttpMessageConverter)),
                ip, port) {
        };
    }

    /**
     * 获取日期信息
     * 
     * @param date
     * @return
     */
    public DayInfo getDayInfo(LocalDate date) {
        var v = infoCache.get(date);
        return v;
    }

    /**
     * 展示缓存日志
     * 
     * @return
     */
    public CacheStats showLog() {
        return infoCache.getStats();
    }
}

package com.kaos.skynet.plugin.timor;

import java.time.Duration;
import java.time.LocalDate;

import com.kaos.skynet.core.http.HttpHandler;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.converter.LocalDateToStringConverter;
import com.kaos.skynet.plugin.timor.entity.DayInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TimorPlugin {
    /**
     * Http句柄
     */
    HttpHandler httpHandler = new HttpHandler("timor.tech", 80);

    /**
     * 时间格式转换器
     */
    LocalDateToStringConverter dateToStringConverter = new LocalDateToStringConverter("yyyy-MM-dd");

    /**
     * 缓存
     */
    @Autowired
    TimorCache timorCache;

    /**
     * 获取日期信息
     * 
     * @param date
     * @return
     */
    public DayInfo getDayInfo(LocalDate date) {
        return timorCache.get(date);
    }

    /**
     * 缓存
     */
    @Component
    class TimorCache extends Cache<LocalDate, DayInfo> {
        TimorCache() {
            super(365, new Converter<LocalDate, DayInfo>() {
                @Override
                public DayInfo convert(LocalDate key) {
                    // 构造请求url
                    String url = "api/holiday/info/{date}";
                    // 从web端获取响应
                    return httpHandler.getForObject(url, DayInfo.class, dateToStringConverter.convert(key));
                };
            }, Duration.ofDays(30), Duration.ofDays(90));
        }
    }
}

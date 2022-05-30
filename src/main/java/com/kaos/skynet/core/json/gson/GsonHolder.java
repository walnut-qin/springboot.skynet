package com.kaos.skynet.core.json.gson;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.skynet.core.json.gson.adapter.cache.CacheTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.date.StandardDateTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.enums.DescriptionEnumTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.local.date.StandardLocalDateTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.local.datime.StandardLocalDateTimeTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.local.time.StandardLocalTimeTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.period.AgePeriodTypeAdapter;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.Enum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component("GsonHolder")
public class GsonHolder {
    /**
     * 构造的内容
     */
    @Getter
    Gson gson;

    @Autowired
    GsonHolder(DescriptionEnumTypeAdapter<Enum> enumTypeAdapter,
            CacheTypeAdapter<Object, Object> cacheTypeAdapter,
            StandardDateTypeAdapter dateTypeAdapter,
            StandardLocalDateTypeAdapter localDateTypeAdapter,
            StandardLocalTimeTypeAdapter localTimeTypeAdapter,
            StandardLocalDateTimeTypeAdapter localDateTimeTypeAdapter,
            AgePeriodTypeAdapter periodTypeAdapter) {
        // 创建构造器
        var builder = new GsonBuilder();

        // 注册枚举适配器
        builder.registerTypeHierarchyAdapter(Enum.class, enumTypeAdapter);

        // 注册缓存适配器
        builder.registerTypeHierarchyAdapter(Cache.class, cacheTypeAdapter);

        // 注册Date解析器
        builder.registerTypeAdapter(Date.class, dateTypeAdapter);

        // 注册LocalDate解析器
        builder.registerTypeAdapter(LocalDate.class, localDateTypeAdapter);

        // 注册LocalTime解析器
        builder.registerTypeAdapter(LocalTime.class, localTimeTypeAdapter);

        // 注册LocalDateTime解析器
        builder.registerTypeAdapter(LocalDateTime.class, localDateTimeTypeAdapter);

        // 注解Period解析器 - 默认为年龄解析
        builder.registerTypeAdapter(Period.class, periodTypeAdapter);

        // 创建gson对象
        this.gson = builder.create();
    }
}

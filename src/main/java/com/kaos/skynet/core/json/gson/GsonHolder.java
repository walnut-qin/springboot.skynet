package com.kaos.skynet.core.json.gson;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.skynet.core.json.gson.adapter.CacheTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.DateTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.EnumTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.LocalDateTimeTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.LocalDateTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.LocalTimeTypeAdapter;
import com.kaos.skynet.core.json.gson.adapter.PeriodTypeAdapter;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.Enum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
public class GsonHolder {
    /**
     * 构造的内容
     */
    @Getter
    Gson gson;

    @Autowired
    GsonHolder() {
        // 创建构造器
        var builder = new GsonBuilder();

        // 注册枚举适配器
        builder.registerTypeHierarchyAdapter(Enum.class, new EnumTypeAdapter<>());

        // 注册缓存适配器
        builder.registerTypeHierarchyAdapter(Cache.class, new CacheTypeAdapter<>());

        // 注册Date解析器
        builder.registerTypeAdapter(Date.class, new DateTypeAdapter());

        // 注册LocalDate解析器
        builder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());

        // 注册LocalTime解析器
        builder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());

        // 注册LocalDateTime解析器
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());

        // 注解Period解析器 - 默认为年龄解析
        builder.registerTypeAdapter(Period.class, new PeriodTypeAdapter());

        // 创建gson对象
        this.gson = builder.create();
    }
}

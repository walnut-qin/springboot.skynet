package com.kaos.skynet.core.type.json;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.skynet.core.type.Cache;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.json.gson.adapter.cache.CacheTypeAdapter;
import com.kaos.skynet.core.type.json.gson.adapter.date.StandardDateTypeAdapter;
import com.kaos.skynet.core.type.json.gson.adapter.enums.DescriptionEnumTypeAdapter;
import com.kaos.skynet.core.type.json.gson.adapter.local.date.StandardLocalDateTypeAdapter;
import com.kaos.skynet.core.type.json.gson.adapter.local.datime.StandardLocalDateTimeTypeAdapter;
import com.kaos.skynet.core.type.json.gson.adapter.local.time.StandardLocalTimeTypeAdapter;
import com.kaos.skynet.core.type.json.gson.adapter.period.AgePeriodTypeAdapter;

import org.springframework.stereotype.Component;

@Component
public class Json {
    /**
     * 序列化工具
     */
    Gson gson;

    Json(DescriptionEnumTypeAdapter<Enum> enumTypeAdapter,
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

    /**
     * 序列化
     * 
     * @param src
     * @return
     */
    public String toJson(Object src) {
        return gson.toJson(src);
    }

    /**
     * 反序列化
     * 
     * @param <T>
     * @param json
     * @param classOfT
     * @return
     */
    public <T> T fromJson(String json, Class<T> classOfT) {// 反射出classOfV
        return gson.fromJson(json, classOfT);
    }

    /**
     * 深拷贝
     * 
     * @param <T>
     * @param src
     * @param classOfT
     * @return
     */
    public <T> T clone(T src, Class<T> classOfT) {
        // 序列化
        String str = this.toJson(src);

        // 反序列化
        return this.fromJson(str, classOfT);
    }
}

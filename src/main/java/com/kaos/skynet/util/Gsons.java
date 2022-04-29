package com.kaos.skynet.util;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kaos.skynet.config.converter.EnumTypeConverter;
import com.kaos.skynet.enums.Enum;

public final class Gsons {
    /**
     * 枚举适配器
     */
    static class EnumTypeAdapter<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(((Enum) src).getDescription());
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws com.google.gson.JsonParseException {
            if (json != null) {
                // 构造转换器
                EnumTypeConverter<E> enumTypeConverter = new EnumTypeConverter<>((Class<E>) typeOfT);

                // 执行转换
                return enumTypeConverter.convert(json.getAsString());
            }
            return null;
        }
    }

    /**
     * LocalDate解析器
     */
    static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            return null;
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            return null;
        }
    }

    /**
     * LocalDate解析器
     */
    static class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
        @Override
        public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
            return null;
        }

        @Override
        public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                return LocalTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
            return null;
        }
    }

    /**
     * LocalDate解析器
     */
    static class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            return null;
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
            return null;
        }
    }

    /**
     * 创建通用格式的gson对象
     */
    public static Gson newGson() {
        var builder = new GsonBuilder();

        // 注册时间格式
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 注册枚举适配器
        builder.registerTypeHierarchyAdapter(Enum.class, new EnumTypeAdapter<>());

        // 注册LocalDate解析器
        builder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter());

        // 注册LocalTime解析器
        builder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter());

        // 注册LocalDateTime解析器
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter());

        // 序列化空值
        // builder.serializeNulls();

        return builder.create();
    }
}

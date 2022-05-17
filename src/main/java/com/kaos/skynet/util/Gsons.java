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
import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;

public final class Gsons {
    /**
     * 创建通用格式的gson对象
     */
    public static Gson newGson() {
        var builder = new GsonBuilder();

        // 注册时间格式
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 注册枚举适配器
        builder.registerTypeHierarchyAdapter(Enum.class, new EnumTypeAdapter<>(false));

        // 注册LocalDate解析器
        builder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter("yyyy-MM-dd"));

        // 注册LocalTime解析器
        builder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter("HH:mm:ss"));

        // 注册LocalDateTime解析器
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter("yyyy-MM-dd HH:mm:ss"));

        return builder.create();
    }

    /**
     * 创建通用格式的gson对象
     */
    public static Gson newGson(Boolean inverseEnum, String dateFormat, String timeFormat, String dateTimeFormat) {
        var builder = new GsonBuilder();

        // 注册时间格式
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 注册枚举适配器
        builder.registerTypeHierarchyAdapter(Enum.class, new EnumTypeAdapter<>(inverseEnum));

        // 注册LocalDate解析器
        builder.registerTypeAdapter(LocalDate.class, new LocalDateTypeAdapter(dateFormat));

        // 注册LocalTime解析器
        builder.registerTypeAdapter(LocalTime.class, new LocalTimeTypeAdapter(timeFormat));

        // 注册LocalDateTime解析器
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter(dateTimeFormat));

        return builder.create();
    }

    /**
     * 枚举适配器
     */
    @AllArgsConstructor
    public static class EnumTypeAdapter<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
        /**
         * 正反序列化字段反转
         */
        Boolean inverse = null;

        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                if (inverse) {
                    return new JsonPrimitive(((Enum) src).getValue());
                } else {
                    return new JsonPrimitive(((Enum) src).getDescription());
                }
            }
            return null;
        }

        @SuppressWarnings("unchecked")
        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws com.google.gson.JsonParseException {
            if (json != null) {
                // 构造转换器
                EnumTypeConverter<E> enumTypeConverter = new EnumTypeConverter<>((Class<E>) typeOfT, this.inverse);

                // 执行转换
                return enumTypeConverter.convert(json.getAsString());
            }
            return null;
        }
    }

    /**
     * LocalDate解析器
     */
    @AllArgsConstructor
    public static class LocalDateTypeAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        /**
         * 格式串
         */
        String format = null;

        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(format)));
            }
            return null;
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                return LocalDate.parse(json.getAsString(), DateTimeFormatter.ofPattern(format));
            }
            return null;
        }
    }

    /**
     * LocalDate解析器
     */
    @AllArgsConstructor
    public static class LocalTimeTypeAdapter implements JsonSerializer<LocalTime>, JsonDeserializer<LocalTime> {
        /**
         * 格式串
         */
        String format = null;

        @Override
        public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(format)));
            }
            return null;
        }

        @Override
        public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                return LocalTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(format));
            }
            return null;
        }
    }

    /**
     * LocalDate解析器
     */
    @AllArgsConstructor
    public static class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        /**
         * 格式串
         */
        String format = null;

        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern(format)));
            }
            return null;
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            if (json != null) {
                return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ofPattern(format));
            }
            return null;
        }
    }
}

package com.kaos.his.util;

import java.lang.reflect.Type;

import com.google.common.base.Optional;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kaos.his.config.converter.EnumTypeConverter;
import com.kaos.his.enums.Enum;

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

    static class OptionalTypeAdapter<E extends Optional<?>> implements JsonSerializer<E>, JsonDeserializer<E> {
        @Override
        public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
            if (src != null) {
                return context.serialize(src.orNull());
            }
            return null;
        }

        @Override
        public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            throw new RuntimeException("无法反序列化到Optional对象");
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

        // 注册Optional适配器
        // builder.registerTypeHierarchyAdapter(Optional.class, new OptionalTypeAdapter<>());

        builder.serializeNulls();

        return builder.create();
    }
}

package com.kaos.helper.gson.adapter;

import java.lang.reflect.Type;

import com.google.gson.*;
import com.kaos.his.config.converter.EnumTypeConverter;
import com.kaos.his.enums.IEnum;

public class EnumTypeAdapter<E extends IEnum> implements JsonSerializer<E>, JsonDeserializer<E> {
    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(((IEnum) src).getDescription());
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json != null) {
            // 构造转换器
            EnumTypeConverter<E> enumTypeConverter = new EnumTypeConverter<>((Class<E>) typeOfT);

            // 执行转换
            return enumTypeConverter.convert(json.getAsString());
        }
        return null;
    }
}

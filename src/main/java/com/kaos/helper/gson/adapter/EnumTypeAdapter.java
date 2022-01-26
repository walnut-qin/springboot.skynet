package com.kaos.helper.gson.adapter;

import java.lang.reflect.Type;

import com.google.gson.*;
import com.kaos.helper.gson.converter.EnumTypeConverter;
import com.kaos.inf.IEnum;

public class EnumTypeAdapter<E extends IEnum> implements JsonSerializer<E>, JsonDeserializer<E> {
    /**
     * 枚举转换器
     */
    EnumTypeConverter<E> enumTypeConverter = null;

    /**
     * 构造函数
     * 
     * @param classOfE 泛型E的class对象
     */
    public EnumTypeAdapter(Class<E> classOfE) {
        this.enumTypeConverter = new EnumTypeConverter<>(classOfE);
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(((IEnum) src).getDescription());
        }
        return null;
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        if (json != null) {
            return this.enumTypeConverter.convert(json.getAsString());
        }
        return null;
    }
}

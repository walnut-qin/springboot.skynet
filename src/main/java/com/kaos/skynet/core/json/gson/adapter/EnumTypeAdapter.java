package com.kaos.skynet.core.json.gson.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.EnumToStringConverter;
import com.kaos.skynet.core.type.converter.StringToEnumConverterFactory;

public class EnumTypeAdapter<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
    /**
     * 写转换器
     */
    protected StringToEnumConverterFactory rConverterFactory = new StringToEnumConverterFactory(false);

    /**
     * 读转换器
     */
    protected EnumToStringConverter<E> wConverter = new EnumToStringConverter<>(false);

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(wConverter.convert(src));
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // 执行转换
        return rConverterFactory.getConverter((Class<E>) typeOfT).convert(json.getAsString());
    }
}

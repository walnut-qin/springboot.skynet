package com.kaos.skynet.core.util.json.adapter;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.util.converter.EnumToStringConverter;
import com.kaos.skynet.core.util.converter.StringToEnumConverterFactory;

/**
 * 反转枚举适配器，使用值域作为判断依据，因使用频繁，收入核心库
 */
public class EnumTypeAdapter_Value<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
    /**
     * 读转换器
     */
    StringToEnumConverterFactory rConverterFactory = new StringToEnumConverterFactory(true);

    /**
     * 写转换器
     */
    EnumToStringConverter<E> wConverter = new EnumToStringConverter<>(true);

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(wConverter.convert(src));
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws com.google.gson.JsonParseException {
        // 执行转换
        return rConverterFactory.getConverter((Class<E>) typeOfT).convert(json.getAsString());
    }
}

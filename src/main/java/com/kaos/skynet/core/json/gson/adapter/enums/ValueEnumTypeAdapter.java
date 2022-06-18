package com.kaos.skynet.core.json.gson.adapter.enums;

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

import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Log4j
@Component("ValueEnumTypeAdapter")
public class ValueEnumTypeAdapter<E extends Enum> implements JsonSerializer<E>, JsonDeserializer<E> {
    // 正向转换器, 由于存在无参构造函数，可以预构造
    final EnumToStringConverter<E> enumToStringConverter = new EnumToStringConverter<>(true);

    // 逆向转换器, 构造需要传入泛型E的类型，动态构造
    final StringToEnumConverterFactory stringToEnumConverterFactory = new StringToEnumConverterFactory(true);

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(enumToStringConverter.convert(src));
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        // 类型强转
        Class<E> classOfE = null;
        try {
            classOfE = (Class<E>) typeOfT;
        } catch (Exception e) {
            log.error(String.format("反序列化失败(%s)", e.getMessage()));
            throw new RuntimeException(e);
        }

        // 执行转换
        return stringToEnumConverterFactory.getConverter(classOfE).convert(json.getAsString());
    }
}

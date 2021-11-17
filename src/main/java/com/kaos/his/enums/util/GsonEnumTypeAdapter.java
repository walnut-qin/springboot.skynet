package com.kaos.his.enums.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class GsonEnumTypeAdapter<E extends IEnum<E>> implements JsonSerializer<E>, JsonDeserializer<E> {

    private E[] enums;

    public GsonEnumTypeAdapter(Class<E> typeOfE) {
        this.enums = typeOfE.getEnumConstants();
    }

    @Override
    public JsonElement serialize(E src, Type typeOfSrc, JsonSerializationContext context) {
        if (src != null) {
            return new JsonPrimitive(((IEnum<E>) src).getDescription());
        }
        return null;
    }

    @Override
    public E deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (json != null) {
            var description = json.getAsString();
            for (E e : enums) {
                if (e.getDescription().equals(description)) {
                    return e;
                }
            }
        }
        return null;
    }
}

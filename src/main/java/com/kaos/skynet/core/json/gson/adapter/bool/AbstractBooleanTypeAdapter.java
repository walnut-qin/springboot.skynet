package com.kaos.skynet.core.json.gson.adapter.bool;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbstractBooleanTypeAdapter extends TypeAdapter<Boolean> {
    /**
     * LocalDate转字符串的转换器
     */
    BooleanToStringConverter booleanToStringConverter;

    /**
     * 字符串转LocalDate的转换器
     */
    StringToBooleanConverter stringToBooleanConverter;

    @Override
    public Boolean read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return stringToBooleanConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(booleanToStringConverter.convert(value));
        }
    }
}

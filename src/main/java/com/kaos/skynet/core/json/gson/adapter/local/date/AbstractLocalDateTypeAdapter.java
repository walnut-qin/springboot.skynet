package com.kaos.skynet.core.json.gson.adapter.local.date;

import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.LocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractLocalDateTypeAdapter extends TypeAdapter<LocalDate> {
    /**
     * LocalDate转字符串的转换器
     */
    LocalDateToStringConverter localDateToStringConverter;

    /**
     * 字符串转LocalDate的转换器
     */
    StringToLocalDateConverter stringToLocalDateConverter;

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return stringToLocalDateConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(localDateToStringConverter.convert(value));
        }
    }
}

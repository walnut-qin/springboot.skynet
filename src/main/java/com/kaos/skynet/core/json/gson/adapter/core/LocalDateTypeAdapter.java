package com.kaos.skynet.core.json.gson.adapter.core;

import java.io.IOException;
import java.time.LocalDate;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.LocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;

public class LocalDateTypeAdapter extends TypeAdapter<LocalDate> {
    /**
     * 字符串转LocalDate的转换器
     */
    protected StringToLocalDateConverter rConverter = new StringToLocalDateConverter("yyyy-MM-dd");

    /**
     * LocalDate转字符串的转换器
     */
    protected LocalDateToStringConverter wConverter = new LocalDateToStringConverter("yyyy-MM-dd");

    @Override
    public LocalDate read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return rConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, LocalDate value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(wConverter.convert(value));
        }
    }
}

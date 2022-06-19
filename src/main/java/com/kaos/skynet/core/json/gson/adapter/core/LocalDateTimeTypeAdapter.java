package com.kaos.skynet.core.json.gson.adapter.core;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.LocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateTimeConverter;

public class LocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    /**
     * 字符串转LocalDate的转换器
     */
    protected StringToLocalDateTimeConverter rConverter = new StringToLocalDateTimeConverter("yyyy-MM-dd HH:mm:ss");

    /**
     * LocalDate转字符串的转换器
     */
    protected LocalDateTimeToStringConverter wConverter = new LocalDateTimeToStringConverter("yyyy-MM-dd HH:mm:ss");

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return rConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(wConverter.convert(value));
        }
    }
}

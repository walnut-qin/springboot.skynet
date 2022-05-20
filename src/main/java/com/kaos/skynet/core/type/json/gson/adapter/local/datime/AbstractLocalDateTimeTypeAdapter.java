package com.kaos.skynet.core.type.json.gson.adapter.local.datime;

import java.io.IOException;
import java.time.LocalDateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.local.datime.AbstractLocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.AbstractStringToLocalDateTimeConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractLocalDateTimeTypeAdapter extends TypeAdapter<LocalDateTime> {
    /**
     * LocalDate转字符串的转换器
     */
    AbstractLocalDateTimeToStringConverter localDateTimeToStringConverter;

    /**
     * 字符串转LocalDate的转换器
     */
    AbstractStringToLocalDateTimeConverter stringToLocalDateTimeConverter;

    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return stringToLocalDateTimeConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(localDateTimeToStringConverter.convert(value));
        }
    }
}

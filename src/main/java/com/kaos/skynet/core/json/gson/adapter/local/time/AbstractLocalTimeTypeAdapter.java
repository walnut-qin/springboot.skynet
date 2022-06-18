package com.kaos.skynet.core.json.gson.adapter.local.time;

import java.io.IOException;
import java.time.LocalTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.LocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractLocalTimeTypeAdapter extends TypeAdapter<LocalTime> {
    /**
     * LocalDate转字符串的转换器
     */
    LocalTimeToStringConverter localTimeToStringConverter;

    /**
     * 字符串转LocalDate的转换器
     */
    StringToLocalTimeConverter stringToLocalTimeConverter;

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return stringToLocalTimeConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(localTimeToStringConverter.convert(value));
        }
    }
}

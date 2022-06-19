package com.kaos.skynet.core.json.gson.adapter.core;

import java.io.IOException;
import java.time.LocalTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.LocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

public class LocalTimeTypeAdapter extends TypeAdapter<LocalTime> {
    /**
     * 字符串转LocalDate的转换器
     */
    protected StringToLocalTimeConverter rConverter = new StringToLocalTimeConverter("HH:mm:ss");

    /**
     * LocalDate转字符串的转换器
     */
    protected LocalTimeToStringConverter wConverter = new LocalTimeToStringConverter("HH:mm:ss");

    @Override
    public LocalTime read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return rConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, LocalTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(wConverter.convert(value));
        }
    }
}

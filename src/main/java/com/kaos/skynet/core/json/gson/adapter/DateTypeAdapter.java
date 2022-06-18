package com.kaos.skynet.core.json.gson.adapter;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.DateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToDateConverter;

public class DateTypeAdapter extends TypeAdapter<Date> {
    /**
     * 字符串转LocalDate的转换器
     */
    protected StringToDateConverter rConverter;

    /**
     * LocalDate转字符串的转换器
     */
    protected DateToStringConverter wConverter;

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return rConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(wConverter.convert(value));
        }
    }
}

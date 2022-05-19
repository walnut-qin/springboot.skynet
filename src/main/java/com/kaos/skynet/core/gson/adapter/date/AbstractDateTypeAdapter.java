package com.kaos.skynet.core.gson.adapter.date;

import java.io.IOException;
import java.util.Date;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.date.string.AbstractDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.AbstractStringToDateConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractDateTypeAdapter extends TypeAdapter<Date> {
    /**
     * LocalDate转字符串的转换器
     */
    AbstractDateToStringConverter dateToStringConverter;

    /**
     * 字符串转LocalDate的转换器
     */
    AbstractStringToDateConverter stringToDateConverter;

    @Override
    public Date read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return stringToDateConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, Date value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(dateToStringConverter.convert(value));
        }
    }
}

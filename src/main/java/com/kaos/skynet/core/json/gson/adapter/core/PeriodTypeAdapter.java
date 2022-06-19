package com.kaos.skynet.core.json.gson.adapter.core;

import java.io.IOException;
import java.time.Period;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.PeriodToStringConverter;

public class PeriodTypeAdapter extends TypeAdapter<Period> {
    /**
     * 写转换器
     */
    protected PeriodToStringConverter wConverter = new PeriodToStringConverter("岁", "月", "天", true);

    @Override
    public Period read(JsonReader in) throws IOException {
        return null;
    }

    @Override
    public void write(JsonWriter out, Period value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(wConverter.convert(value));
        }
    }
}

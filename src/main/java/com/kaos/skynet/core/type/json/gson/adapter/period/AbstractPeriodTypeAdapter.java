package com.kaos.skynet.core.type.json.gson.adapter.period;

import java.io.IOException;
import java.time.Period;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.period.string.AbstractPeriodToStringConverter;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@AllArgsConstructor
public abstract class AbstractPeriodTypeAdapter extends TypeAdapter<Period> {
    /**
     * LocalDate转字符串的转换器
     */
    AbstractPeriodToStringConverter periodToStringConverter;

    @Override
    public Period read(JsonReader in) throws IOException {
        log.error("不支持的类型转换(String -> Period)");
        throw new RuntimeException("不支持的类型转换(String -> Period)");
    }

    @Override
    public void write(JsonWriter out, Period value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(periodToStringConverter.convert(value));
        }
    }
}

package com.kaos.skynet.core.json.gson.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

/**
 * Bool型处理器，不纳入默认处理器，只因使用频繁，纳入核心库
 */
public class BooleanTypeAdapter extends TypeAdapter<Boolean> {
    /**
     * 真值
     */
    protected StringToBooleanConverter rConverter = new StringToBooleanConverter("是", "否");

    /**
     * 假值
     */
    protected BooleanToStringConverter wConverter = new BooleanToStringConverter("是", "否");

    @Override
    public Boolean read(JsonReader in) throws IOException {
        if (in.peek() == null) {
            return null;
        } else {
            return rConverter.convert(in.nextString());
        }
    }

    @Override
    public void write(JsonWriter out, Boolean value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(wConverter.convert(value));
        }
    }
}

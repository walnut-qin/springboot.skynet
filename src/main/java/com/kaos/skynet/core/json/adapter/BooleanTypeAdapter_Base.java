package com.kaos.skynet.core.json.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

/**
 * Bool型处理器，不纳入默认处理器，只因使用频繁，纳入核心库
 */
abstract class BooleanTypeAdapter_Base extends TypeAdapter<Boolean> {
    /**
     * 真值
     */
    StringToBooleanConverter rConverter;

    /**
     * 假值
     */
    BooleanToStringConverter wConverter;

    /**
     * 构造函数
     * 
     * @param trueValue
     * @param falseValue
     */
    public BooleanTypeAdapter_Base(String trueValue, String falseValue) {
        rConverter = new StringToBooleanConverter(trueValue, falseValue);
        wConverter = new BooleanToStringConverter(trueValue, falseValue);
    }

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

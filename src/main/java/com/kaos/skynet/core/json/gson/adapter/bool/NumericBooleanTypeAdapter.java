package com.kaos.skynet.core.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.NumericBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.NumericStringToBooleanConverter;

import org.springframework.stereotype.Component;

@Component("NumericBooleanTypeAdapter")
public class NumericBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public NumericBooleanTypeAdapter() {
        super(new NumericBooleanToStringConverter(), new NumericStringToBooleanConverter());
    }
}

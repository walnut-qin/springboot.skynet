package com.kaos.skynet.core.type.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.NumericBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.NumericStringToBooleanConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("NumericBooleanTypeAdapter")
public class NumericBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    @Autowired
    public NumericBooleanTypeAdapter(NumericBooleanToStringConverter booleanToStringConverter,
            NumericStringToBooleanConverter stringToBooleanConverter) {
        super(booleanToStringConverter, stringToBooleanConverter);
    }
}

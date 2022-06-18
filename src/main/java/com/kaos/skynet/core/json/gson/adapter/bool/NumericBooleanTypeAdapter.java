package com.kaos.skynet.core.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

import org.springframework.stereotype.Component;

@Component("NumericBooleanTypeAdapter")
public class NumericBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public NumericBooleanTypeAdapter() {
        super(new BooleanToStringConverter("1", "0"), new StringToBooleanConverter("1", "0"));
    }
}

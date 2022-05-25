package com.kaos.skynet.core.type.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.NumericBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.NumericStringToBooleanConverter;

public class NumericBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public NumericBooleanTypeAdapter() {
        super(new NumericBooleanToStringConverter(), new NumericStringToBooleanConverter());
    }
}

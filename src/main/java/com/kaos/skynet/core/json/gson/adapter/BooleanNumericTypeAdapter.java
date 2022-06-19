package com.kaos.skynet.core.json.gson.adapter;

import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

public class BooleanNumericTypeAdapter extends BooleanTypeAdapter {
    public BooleanNumericTypeAdapter() {
        super(new StringToBooleanConverter("1", "0"), new BooleanToStringConverter("1", "0"));
    }
}

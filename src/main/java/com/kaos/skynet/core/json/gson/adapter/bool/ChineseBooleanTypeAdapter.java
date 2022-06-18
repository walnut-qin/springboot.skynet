package com.kaos.skynet.core.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

import org.springframework.stereotype.Component;

@Component("ChineseBooleanTypeAdapter")
public class ChineseBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public ChineseBooleanTypeAdapter() {
        super(new BooleanToStringConverter("是", "否"), new StringToBooleanConverter("是", "否"));
    }
}

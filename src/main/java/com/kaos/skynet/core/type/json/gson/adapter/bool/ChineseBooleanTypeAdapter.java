package com.kaos.skynet.core.type.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.ChineseBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.ChineseStringToBooleanConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("ChineseBooleanTypeAdapter")
public class ChineseBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    @Autowired
    public ChineseBooleanTypeAdapter(ChineseBooleanToStringConverter booleanToStringConverter,
            ChineseStringToBooleanConverter stringToBooleanConverter) {
        super(booleanToStringConverter, stringToBooleanConverter);
    }
}

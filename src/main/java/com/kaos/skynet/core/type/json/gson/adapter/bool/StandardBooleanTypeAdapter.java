package com.kaos.skynet.core.type.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.StandardBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.StandardStringToBooleanConverter;

import org.springframework.stereotype.Component;

@Component("StandardBooleanTypeAdapter")
public class StandardBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public StandardBooleanTypeAdapter(StandardBooleanToStringConverter booleanToStringConverter,
            StandardStringToBooleanConverter stringToBooleanConverter) {
        super(booleanToStringConverter, stringToBooleanConverter);
    }
}

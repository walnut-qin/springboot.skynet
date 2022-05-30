package com.kaos.skynet.core.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.StandardBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.StandardStringToBooleanConverter;

import org.springframework.stereotype.Component;

@Component("StandardBooleanTypeAdapter")
public class StandardBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public StandardBooleanTypeAdapter() {
        super(new StandardBooleanToStringConverter(), new StandardStringToBooleanConverter());
    }
}

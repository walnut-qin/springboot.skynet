package com.kaos.skynet.core.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

import org.springframework.stereotype.Component;

@Component("StandardBooleanTypeAdapter")
public class StandardBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public StandardBooleanTypeAdapter() {
        super(new BooleanToStringConverter("true", "false"), new StringToBooleanConverter("true", "false"));
    }
}

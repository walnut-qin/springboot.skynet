package com.kaos.skynet.core.type.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.StandardBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.StandardStringToBooleanConverter;

public class StandardBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public StandardBooleanTypeAdapter() {
        super(new StandardBooleanToStringConverter(), new StandardStringToBooleanConverter());
    }
}

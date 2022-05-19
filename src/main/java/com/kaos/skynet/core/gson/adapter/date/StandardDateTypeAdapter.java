package com.kaos.skynet.core.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.StandardDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.StandardStringToDateConverter;

public class StandardDateTypeAdapter extends AbstractDateTypeAdapter {
    public StandardDateTypeAdapter() {
        super(new StandardDateToStringConverter(), new StandardStringToDateConverter());
    }
}

package com.kaos.skynet.core.gson.adapter.date;

import com.kaos.skynet.core.type.converter.local.date.string.StandardLocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.date.StandardStringToLocalDateConverter;

public class StandardLocalDateTypeAdapter extends AbstractLocalDateTypeAdapter {
    public StandardLocalDateTypeAdapter() {
        super(new StandardLocalDateToStringConverter(), new StandardStringToLocalDateConverter());
    }
}

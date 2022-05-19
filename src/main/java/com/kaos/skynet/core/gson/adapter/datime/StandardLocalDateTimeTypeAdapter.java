package com.kaos.skynet.core.gson.adapter.datime;

import com.kaos.skynet.core.type.converter.local.datime.StandardLocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.StandardStringToLocalDateTimeConverter;

public class StandardLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public StandardLocalDateTimeTypeAdapter() {
        super(new StandardLocalDateTimeToStringConverter(), new StandardStringToLocalDateTimeConverter());
    }
}

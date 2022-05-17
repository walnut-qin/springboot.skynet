package com.kaos.skynet.core.gson.adapter.datime;

import com.kaos.skynet.core.type.converter.datime.CompactLocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.datime.CompactStringToLocalDateTimeConverter;

public class CompactLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public CompactLocalDateTimeTypeAdapter() {
        super(new CompactLocalDateTimeToStringConverter(), new CompactStringToLocalDateTimeConverter());
    }
}

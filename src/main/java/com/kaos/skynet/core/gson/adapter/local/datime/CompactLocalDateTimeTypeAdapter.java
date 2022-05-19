package com.kaos.skynet.core.gson.adapter.local.datime;

import com.kaos.skynet.core.type.converter.local.datime.CompactLocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.CompactStringToLocalDateTimeConverter;

public class CompactLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public CompactLocalDateTimeTypeAdapter() {
        super(new CompactLocalDateTimeToStringConverter(), new CompactStringToLocalDateTimeConverter());
    }
}

package com.kaos.skynet.core.type.json.gson.adapter.local.datime;

import com.kaos.skynet.core.type.converter.local.datime.CompactLocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.CompactStringToLocalDateTimeConverter;

import org.springframework.stereotype.Component;

@Component("CompactLocalDateTimeTypeAdapter")
public class CompactLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public CompactLocalDateTimeTypeAdapter() {
        super(new CompactLocalDateTimeToStringConverter(), new CompactStringToLocalDateTimeConverter());
    }
}

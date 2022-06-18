package com.kaos.skynet.core.json.gson.adapter.local.datime;

import com.kaos.skynet.core.type.converter.LocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateTimeConverter;

import org.springframework.stereotype.Component;

@Component("CompactLocalDateTimeTypeAdapter")
public class CompactLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public CompactLocalDateTimeTypeAdapter() {
        super(new LocalDateTimeToStringConverter("yyyyMMddHHmmss"),
                new StringToLocalDateTimeConverter("yyyyMMddHHmmss"));
    }
}

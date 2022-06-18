package com.kaos.skynet.core.json.gson.adapter.local.date;

import com.kaos.skynet.core.type.converter.LocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;

import org.springframework.stereotype.Component;

@Component("CompactLocalDateTypeAdapter")
public class CompactLocalDateTypeAdapter extends AbstractLocalDateTypeAdapter {
    public CompactLocalDateTypeAdapter() {
        super(new LocalDateToStringConverter("yyyyMMdd"), new StringToLocalDateConverter("yyyyMMdd"));
    }
}

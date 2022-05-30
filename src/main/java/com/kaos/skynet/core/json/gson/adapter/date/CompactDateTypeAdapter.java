package com.kaos.skynet.core.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.CompactDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.CompactStringToDateConverter;

import org.springframework.stereotype.Component;

@Component("CompactDateTypeAdapter")
public class CompactDateTypeAdapter extends AbstractDateTypeAdapter {
    public CompactDateTypeAdapter() {
        super(new CompactDateToStringConverter(), new CompactStringToDateConverter());
    }
}

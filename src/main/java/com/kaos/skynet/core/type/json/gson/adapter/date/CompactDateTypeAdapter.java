package com.kaos.skynet.core.type.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.CompactDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.CompactStringToDateConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("CompactDateTypeAdapter")
public class CompactDateTypeAdapter extends AbstractDateTypeAdapter {
    @Autowired
    public CompactDateTypeAdapter(CompactDateToStringConverter dateToStringConverter,
            CompactStringToDateConverter stringToDateConverter) {
        super(dateToStringConverter, stringToDateConverter);
    }
}

package com.kaos.skynet.core.type.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.StandardDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.StandardStringToDateConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("StandardDateTypeAdapter")
public class StandardDateTypeAdapter extends AbstractDateTypeAdapter {
    @Autowired
    public StandardDateTypeAdapter(StandardDateToStringConverter dateToStringConverter,
            StandardStringToDateConverter stringToDateConverter) {
        super(dateToStringConverter, stringToDateConverter);
    }
}

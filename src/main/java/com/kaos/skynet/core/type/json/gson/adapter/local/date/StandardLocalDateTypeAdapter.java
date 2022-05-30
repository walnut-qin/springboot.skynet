package com.kaos.skynet.core.type.json.gson.adapter.local.date;

import com.kaos.skynet.core.type.converter.local.date.string.StandardLocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.date.StandardStringToLocalDateConverter;

import org.springframework.stereotype.Component;

@Component("StandardLocalDateTypeAdapter")
public class StandardLocalDateTypeAdapter extends AbstractLocalDateTypeAdapter {
    public StandardLocalDateTypeAdapter() {
        super(new StandardLocalDateToStringConverter(), new StandardStringToLocalDateConverter());
    }
}

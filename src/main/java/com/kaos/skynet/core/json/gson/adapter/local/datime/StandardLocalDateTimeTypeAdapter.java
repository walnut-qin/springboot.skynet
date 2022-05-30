package com.kaos.skynet.core.json.gson.adapter.local.datime;

import com.kaos.skynet.core.type.converter.local.datime.StandardLocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.StandardStringToLocalDateTimeConverter;

import org.springframework.stereotype.Component;

@Component("StandardLocalDateTimeTypeAdapter")
public class StandardLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public StandardLocalDateTimeTypeAdapter() {
        super(new StandardLocalDateTimeToStringConverter(), new StandardStringToLocalDateTimeConverter());
    }
}

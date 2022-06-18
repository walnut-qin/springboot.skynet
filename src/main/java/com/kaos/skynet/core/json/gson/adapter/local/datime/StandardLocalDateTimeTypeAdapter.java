package com.kaos.skynet.core.json.gson.adapter.local.datime;

import com.kaos.skynet.core.type.converter.LocalDateTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateTimeConverter;

import org.springframework.stereotype.Component;

@Component("StandardLocalDateTimeTypeAdapter")
public class StandardLocalDateTimeTypeAdapter extends AbstractLocalDateTimeTypeAdapter {
    public StandardLocalDateTimeTypeAdapter() {
        super(new LocalDateTimeToStringConverter("yyyy-MM-dd HH:mm:ss"), new StringToLocalDateTimeConverter("yyyy-MM-dd HH:mm:ss"));
    }
}

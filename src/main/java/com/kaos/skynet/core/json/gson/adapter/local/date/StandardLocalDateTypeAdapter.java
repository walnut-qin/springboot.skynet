package com.kaos.skynet.core.json.gson.adapter.local.date;

import com.kaos.skynet.core.type.converter.LocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;

import org.springframework.stereotype.Component;

@Component("StandardLocalDateTypeAdapter")
public class StandardLocalDateTypeAdapter extends AbstractLocalDateTypeAdapter {
    public StandardLocalDateTypeAdapter() {
        super(new LocalDateToStringConverter("yyyy-MM-dd"), new StringToLocalDateConverter("yyyy-MM-dd"));
    }
}

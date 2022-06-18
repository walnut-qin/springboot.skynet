package com.kaos.skynet.core.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.DateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToDateConverter;

import org.springframework.stereotype.Component;

@Component("StandardDateTypeAdapter")
public class StandardDateTypeAdapter extends AbstractDateTypeAdapter {
    public StandardDateTypeAdapter() {
        super(new DateToStringConverter("yyyy-MM-dd HH:mm:ss"), new StringToDateConverter("yyyy-MM-dd HH:mm:ss"));
    }
}

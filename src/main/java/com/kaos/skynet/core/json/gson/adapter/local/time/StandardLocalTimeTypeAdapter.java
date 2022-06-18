package com.kaos.skynet.core.json.gson.adapter.local.time;

import com.kaos.skynet.core.type.converter.LocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

import org.springframework.stereotype.Component;

@Component("StandardLocalTimeTypeAdapter")
public class StandardLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public StandardLocalTimeTypeAdapter() {
        super(new LocalTimeToStringConverter("HH:mm:ss"), new StringToLocalTimeConverter("HH:mm:ss"));
    }
}

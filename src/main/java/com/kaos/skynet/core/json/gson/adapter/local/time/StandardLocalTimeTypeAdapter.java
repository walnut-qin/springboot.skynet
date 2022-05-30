package com.kaos.skynet.core.json.gson.adapter.local.time;

import com.kaos.skynet.core.type.converter.local.time.StandardLocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.time.StandardStringToLocalTimeConverter;

import org.springframework.stereotype.Component;

@Component("StandardLocalTimeTypeAdapter")
public class StandardLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public StandardLocalTimeTypeAdapter() {
        super(new StandardLocalTimeToStringConverter(), new StandardStringToLocalTimeConverter());
    }
}

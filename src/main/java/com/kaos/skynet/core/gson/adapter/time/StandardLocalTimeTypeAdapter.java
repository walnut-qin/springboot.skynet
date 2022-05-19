package com.kaos.skynet.core.gson.adapter.time;

import com.kaos.skynet.core.type.converter.string.local.time.StandardStringToLocalTimeConverter;
import com.kaos.skynet.core.type.converter.time.StandardLocalTimeToStringConverter;

public class StandardLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public StandardLocalTimeTypeAdapter() {
        super(new StandardLocalTimeToStringConverter(), new StandardStringToLocalTimeConverter());
    }
}

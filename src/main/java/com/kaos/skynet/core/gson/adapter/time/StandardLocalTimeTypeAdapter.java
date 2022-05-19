package com.kaos.skynet.core.gson.adapter.time;

import com.kaos.skynet.core.type.converter.local.time.StandardLocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.time.StandardStringToLocalTimeConverter;

public class StandardLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public StandardLocalTimeTypeAdapter() {
        super(new StandardLocalTimeToStringConverter(), new StandardStringToLocalTimeConverter());
    }
}

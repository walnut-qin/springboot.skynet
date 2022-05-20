package com.kaos.skynet.core.type.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.CompactDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.CompactStringToDateConverter;

public class CompactDateTypeAdapter extends AbstractDateTypeAdapter {
    public CompactDateTypeAdapter() {
        super(new CompactDateToStringConverter(), new CompactStringToDateConverter());
    }
}

package com.kaos.skynet.core.gson.adapter.date;

import com.kaos.skynet.core.type.converter.date.string.CompactLocalDateToStringConverter;
import com.kaos.skynet.core.type.converter.string.date.CompactStringToLocalDateConverter;

public class CompactLocalDateTypeAdapter extends AbstractLocalDateTypeAdapter {
    public CompactLocalDateTypeAdapter() {
        super(new CompactLocalDateToStringConverter(), new CompactStringToLocalDateConverter());
    }
}

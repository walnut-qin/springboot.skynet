package com.kaos.skynet.core.gson.adapter.time;

import com.kaos.skynet.core.type.converter.string.time.CompactStringToLocalTimeConverter;
import com.kaos.skynet.core.type.converter.time.CompactLocalTimeToStringConverter;

public class CompactLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public CompactLocalTimeTypeAdapter() {
        super(new CompactLocalTimeToStringConverter(), new CompactStringToLocalTimeConverter());
    }
}

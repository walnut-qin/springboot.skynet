package com.kaos.skynet.core.json.gson.adapter.local.time;

import com.kaos.skynet.core.type.converter.local.time.CompactLocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.string.local.time.CompactStringToLocalTimeConverter;

import org.springframework.stereotype.Component;

@Component("CompactLocalTimeTypeAdapter")
public class CompactLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public CompactLocalTimeTypeAdapter() {
        super(new CompactLocalTimeToStringConverter(), new CompactStringToLocalTimeConverter());
    }
}

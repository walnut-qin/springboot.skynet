package com.kaos.skynet.core.json.gson.adapter.local.time;

import com.kaos.skynet.core.type.converter.LocalTimeToStringConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

import org.springframework.stereotype.Component;

@Component("CompactLocalTimeTypeAdapter")
public class CompactLocalTimeTypeAdapter extends AbstractLocalTimeTypeAdapter {
    public CompactLocalTimeTypeAdapter() {
        super(new LocalTimeToStringConverter("HHmmss"), new StringToLocalTimeConverter("HHmmss"));
    }
}

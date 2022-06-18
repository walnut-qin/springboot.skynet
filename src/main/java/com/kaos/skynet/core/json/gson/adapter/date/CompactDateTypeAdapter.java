package com.kaos.skynet.core.json.gson.adapter.date;

import com.kaos.skynet.core.type.converter.DateToStringConverter;
import com.kaos.skynet.core.type.converter.StringToDateConverter;

import org.springframework.stereotype.Component;

@Component("CompactDateTypeAdapter")
public class CompactDateTypeAdapter extends AbstractDateTypeAdapter {
    public CompactDateTypeAdapter() {
        super(new DateToStringConverter("yyyyMMddHHmmss"), new StringToDateConverter("yyyyMMddHHmmss"));
    }
}

package com.kaos.skynet.core.json.gson.adapter.period;

import com.kaos.skynet.core.type.converter.period.string.StandardPeriodToStringConverter;

import org.springframework.stereotype.Component;

@Component("StandardPeriodTypeAdapter")
public class StandardPeriodTypeAdapter extends AbstractPeriodTypeAdapter {
    public StandardPeriodTypeAdapter() {
        super(new StandardPeriodToStringConverter());
    }
}

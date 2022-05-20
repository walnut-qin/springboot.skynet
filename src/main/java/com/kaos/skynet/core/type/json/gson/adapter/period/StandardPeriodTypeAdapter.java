package com.kaos.skynet.core.type.json.gson.adapter.period;

import com.kaos.skynet.core.type.converter.period.string.StandardPeriodToStringConverter;

public class StandardPeriodTypeAdapter extends AbstractPeriodTypeAdapter {
    public StandardPeriodTypeAdapter() {
        super(new StandardPeriodToStringConverter());
    }
}

package com.kaos.skynet.core.type.json.gson.adapter.period;

import com.kaos.skynet.core.type.converter.period.string.AgePeriodToStringConverter;

public class AgePeriodTypeAdapter extends AbstractPeriodTypeAdapter {
    public AgePeriodTypeAdapter() {
        super(new AgePeriodToStringConverter());
    }
}

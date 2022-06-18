package com.kaos.skynet.core.json.gson.adapter.period;

import com.kaos.skynet.core.type.converter.PeriodToStringConverter;

import org.springframework.stereotype.Component;

@Component("AgePeriodTypeAdapter")
public class AgePeriodTypeAdapter extends AbstractPeriodTypeAdapter {
    public AgePeriodTypeAdapter() {
        super(new PeriodToStringConverter("岁", "月", "天", true));
    }
}

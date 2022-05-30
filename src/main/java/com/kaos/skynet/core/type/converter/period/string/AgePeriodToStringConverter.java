package com.kaos.skynet.core.type.converter.period.string;

import org.springframework.stereotype.Component;

@Component("AgePeriodToStringConverter")
public class AgePeriodToStringConverter extends AbstractPeriodToStringConverter {
    public AgePeriodToStringConverter() {
        super(true, "岁", "月", "天");
    }
}

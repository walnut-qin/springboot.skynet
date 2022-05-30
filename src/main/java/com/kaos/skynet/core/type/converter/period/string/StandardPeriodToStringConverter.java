package com.kaos.skynet.core.type.converter.period.string;

import org.springframework.stereotype.Component;

@Component("StandardPeriodToStringConverter")
public class StandardPeriodToStringConverter extends AbstractPeriodToStringConverter {
    public StandardPeriodToStringConverter() {
        super(false, "年", "月", "日");
    }
}

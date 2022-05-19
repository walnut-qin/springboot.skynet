package com.kaos.skynet.core.type.converter.period.string;

public class StandardPeriodToStringConverter extends AbstractPeriodToStringConverter {
    public StandardPeriodToStringConverter() {
        super(false, "年", "月", "日");
    }
}

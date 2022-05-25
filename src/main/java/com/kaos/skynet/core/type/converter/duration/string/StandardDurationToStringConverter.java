package com.kaos.skynet.core.type.converter.duration.string;

public class StandardDurationToStringConverter extends AbstractDurationToStringConverter {
    public StandardDurationToStringConverter() {
        super(false, "日", "时", "分", "秒");
    }
}

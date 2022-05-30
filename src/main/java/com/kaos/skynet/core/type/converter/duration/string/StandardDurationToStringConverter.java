package com.kaos.skynet.core.type.converter.duration.string;

import org.springframework.stereotype.Component;

@Component("StandardDurationToStringConverter")
public class StandardDurationToStringConverter extends AbstractDurationToStringConverter {
    public StandardDurationToStringConverter() {
        super(false, "日", "时", "分", "秒");
    }
}

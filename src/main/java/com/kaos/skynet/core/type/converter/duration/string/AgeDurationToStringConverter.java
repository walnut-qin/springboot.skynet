package com.kaos.skynet.core.type.converter.duration.string;

import org.springframework.stereotype.Component;

@Component("AgeDurationToStringConverter")
public class AgeDurationToStringConverter extends AbstractDurationToStringConverter {
    public AgeDurationToStringConverter() {
        super(true, "天", "小时", "分", "秒");
    }
}

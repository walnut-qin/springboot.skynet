package com.kaos.skynet.core.type.converter.date.string;

import org.springframework.stereotype.Component;

@Component("CompactDateToStringConverter")
public class CompactDateToStringConverter extends AbstractDateToStringConverter {
    public CompactDateToStringConverter() {
        super("yyyyMMddHHmmss");
    }
}

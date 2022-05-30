package com.kaos.skynet.core.type.converter.string.date;

import org.springframework.stereotype.Component;

@Component("CompactStringToDateConverter")
public class CompactStringToDateConverter extends AbstractStringToDateConverter {
    public CompactStringToDateConverter() {
        super("yyyyMMddHHmmss");
    }
}

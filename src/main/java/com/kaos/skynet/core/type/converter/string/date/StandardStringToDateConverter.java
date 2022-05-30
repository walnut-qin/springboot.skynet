package com.kaos.skynet.core.type.converter.string.date;

import org.springframework.stereotype.Component;

@Component("StandardStringToDateConverter")
public class StandardStringToDateConverter extends AbstractStringToDateConverter {
    public StandardStringToDateConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}

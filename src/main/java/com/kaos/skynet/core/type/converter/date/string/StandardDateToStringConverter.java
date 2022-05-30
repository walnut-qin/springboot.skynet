package com.kaos.skynet.core.type.converter.date.string;

import org.springframework.stereotype.Component;

@Component("StandardDateToStringConverter")
public class StandardDateToStringConverter extends AbstractDateToStringConverter {
    public StandardDateToStringConverter() {
        super("yyyy-MM-dd HH:mm:ss");
    }
}

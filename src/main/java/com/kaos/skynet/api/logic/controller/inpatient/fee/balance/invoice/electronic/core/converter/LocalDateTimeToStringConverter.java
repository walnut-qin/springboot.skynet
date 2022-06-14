package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic.core.converter;

import com.kaos.skynet.core.type.converter.local.datime.AbstractLocalDateTimeToStringConverter;

public class LocalDateTimeToStringConverter extends AbstractLocalDateTimeToStringConverter {
    /**
     * 默认构造函数
     */
    public LocalDateTimeToStringConverter() {
        super("yyyyMMddHHmmssSSS");
    }
}

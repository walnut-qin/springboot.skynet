package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic.core.http;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.http.handler.AbstractHttpHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BoSoftHttpHandler extends AbstractHttpHandler {
    BoSoftHttpHandler(JsonHttpMessageConverter jsonHttpMessageConverter) {
        super(new RestTemplate(
                Lists.newArrayList(jsonHttpMessageConverter)),
                "172.16.100.123",
                17001);
    }
}
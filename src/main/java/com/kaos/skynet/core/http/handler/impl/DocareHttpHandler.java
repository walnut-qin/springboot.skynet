package com.kaos.skynet.core.http.handler.impl;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.http.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.http.converter.DoubleHttpMessageConverter;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.http.handler.AbstractHttpHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("DocareHttpHandler")
public class DocareHttpHandler extends AbstractHttpHandler {
    DocareHttpHandler(BooleanHttpMessageConverter booleanHttpMessageConverter,
            DoubleHttpMessageConverter doubleHttpMessageConverter,
            JsonHttpMessageConverter jsonHttpMessageConverter) {
        super(new RestTemplate(
                Lists.newArrayList(booleanHttpMessageConverter, doubleHttpMessageConverter, jsonHttpMessageConverter)),
                "172.16.100.253",
                8025);
    }
}

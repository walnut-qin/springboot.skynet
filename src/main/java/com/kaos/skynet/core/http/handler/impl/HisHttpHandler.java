package com.kaos.skynet.core.http.handler;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.http.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.http.converter.DoubleHttpMessageConverter;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("HisHttpHandler")
public class HisHttpHandler extends AbstractHttpHandler {
    HisHttpHandler(BooleanHttpMessageConverter booleanHttpMessageConverter,
            DoubleHttpMessageConverter doubleHttpMessageConverter,
            JsonHttpMessageConverter jsonHttpMessageConverter) {
        super(new RestTemplate(
                Lists.newArrayList(booleanHttpMessageConverter, doubleHttpMessageConverter, jsonHttpMessageConverter)),
                "172.16.100.252",
                8025);
    }
}

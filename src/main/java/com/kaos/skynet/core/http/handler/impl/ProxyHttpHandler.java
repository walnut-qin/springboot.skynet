package com.kaos.skynet.core.http.handler;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.http.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.http.converter.DoubleHttpMessageConverter;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("ProxyHttpHandler")
public class ProxyHttpHandler extends AbstractHttpHandler {
    ProxyHttpHandler(BooleanHttpMessageConverter booleanHttpMessageConverter,
            DoubleHttpMessageConverter doubleHttpMessageConverter,
            JsonHttpMessageConverter jsonHttpMessageConverter) {
        super(new RestTemplate(
                Lists.newArrayList(booleanHttpMessageConverter, doubleHttpMessageConverter, jsonHttpMessageConverter)),
                "172.16.100.50",
                8025);
    }
}

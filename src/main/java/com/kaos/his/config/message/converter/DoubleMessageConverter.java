package com.kaos.his.config.message.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

public class DoubleMessageConverter extends AbstractHttpMessageConverter<Double> {
    public DoubleMessageConverter() {
        super(new MediaType("text", "plain", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Double.class.isAssignableFrom(clazz);
    }

    @Override
    protected Double readInternal(Class<? extends Double> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        String strVal = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
        return Double.parseDouble(strVal);
    }

    @Override
    protected void writeInternal(Double t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(t.toString().getBytes());
    }
}

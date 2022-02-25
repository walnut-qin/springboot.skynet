package com.kaos.his.config.http.message.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

public class BooleanMessageConverter extends AbstractHttpMessageConverter<Boolean> {
    public BooleanMessageConverter() {
        super(new MediaType("text", "plain", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Boolean.class.isAssignableFrom(clazz);
    }

    @Override
    protected Boolean readInternal(Class<? extends Boolean> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        String strVal = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));
        switch (strVal) {
            case "true":
            case "True":
                return true;

            case "false":
            case "False":
                return false;

            default:
                return null;
        }
    }

    @Override
    protected void writeInternal(Boolean t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(t.toString().getBytes());
    }
}

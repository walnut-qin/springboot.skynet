package com.kaos.skynet.core.type.http.message.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import com.kaos.skynet.core.type.converter.bool.string.StandardBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.StandardStringToBooleanConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

public class BooleanHttpMessageConverter extends AbstractHttpMessageConverter<Boolean> {
    /**
     * Boolean -> String 转换器
     */
    @Autowired
    StandardBooleanToStringConverter standardBooleanToStringConverter;

    /**
     * String -> Boolean 转换器
     */
    @Autowired
    StandardStringToBooleanConverter standardStringToBooleanConverter;

    /**
     * Boolean型的消息处理器
     */
    public BooleanHttpMessageConverter() {
        super(new MediaType("text", "plain", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Boolean.class.isAssignableFrom(clazz);
    }

    @Override
    protected Boolean readInternal(Class<? extends Boolean> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        // 读取原始body字符串
        String strVal = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));

        return this.standardStringToBooleanConverter.convert(strVal);
    }

    @Override
    protected void writeInternal(Boolean t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(this.standardBooleanToStringConverter.convert(t).getBytes());
    }
}

package com.kaos.skynet.core.http.message.converter;

import java.io.IOException;
import java.nio.charset.Charset;

import com.kaos.skynet.core.type.converter.decimal.DoubleToStringConverter;
import com.kaos.skynet.core.type.converter.string.decimal.StringToDoubleConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

public class DoubleHttpMessageConverter extends AbstractHttpMessageConverter<Double> {
    /**
     * Double -> String 转换器
     */
    @Autowired
    DoubleToStringConverter doubleToStringConverter;

    /**
     * String -> Double 转换器
     */
    @Autowired
    StringToDoubleConverter stringToDoubleConverter;

    /**
     * Boolean型的消息处理器
     */
    public DoubleHttpMessageConverter() {
        super(new MediaType("text", "plain", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return Double.class.isAssignableFrom(clazz);
    }

    @Override
    protected Double readInternal(Class<? extends Double> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        // 读取原始body字符串
        String strVal = StreamUtils.copyToString(inputMessage.getBody(), Charset.forName("UTF-8"));

        return this.stringToDoubleConverter.convert(strVal);
    }

    @Override
    protected void writeInternal(Double t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(this.doubleToStringConverter.convert(t).getBytes());
    }
}

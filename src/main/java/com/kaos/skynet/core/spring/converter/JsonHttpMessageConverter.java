package com.kaos.skynet.core.spring.converter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.kaos.skynet.core.json.GsonWrapper;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

public class JsonHttpMessageConverter extends AbstractJsonHttpMessageConverter {
    /**
     * 序列化工具
     */
    final static GsonWrapper gsonWrapper = new GsonWrapper();

    @Override
    protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
        return gsonWrapper.fromJson(reader, resolvedType);
    }

    @Override
    protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
        gsonWrapper.toJson(object, writer);
    }
}

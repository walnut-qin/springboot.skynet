package com.kaos.skynet.core.http.converter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.kaos.skynet.core.http.RspWrapper;
import com.kaos.skynet.core.json.Json;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JsonWrappedHttpMessageConverter extends AbstractJsonHttpMessageConverter {
    /**
     * 序列化工具
     */
    Json json;

    @Override
    protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
        return json.fromJson(reader, resolvedType);
    }

    @Override
    protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
        json.toJson(RspWrapper.wrapSuccessResponse(object), writer);
    }
}

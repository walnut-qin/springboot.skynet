package com.kaos.skynet.core.spring.converter;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.kaos.skynet.core.json.GsonWrapper;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;

import lombok.Getter;

public class JsonWrappedHttpMessageConverter extends AbstractJsonHttpMessageConverter {
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
        gsonWrapper.toJson(RspWrapper.wrapSuccessResponse(object), writer);
    }

    /**
     * 响应封装器
     */
    @Getter
    public static class RspWrapper<T> {
        /**
         * 响应码 0: 成功; 1: 失败
         */
        private Integer code;

        /**
         * 响应信息
         */
        private String message;

        /**
         * 响应数据
         */
        private T data;

        /**
         * 私有化构造函数
         */
        private RspWrapper() {
        }

        /**
         * 构造成功响应
         * 
         * @param <T>
         * @param data
         * @return
         */
        public static <T> RspWrapper<T> wrapSuccessResponse(T data) {
            RspWrapper<T> response = new RspWrapper<>();
            response.code = 0;
            response.data = data;
            return response;
        }

        /**
         * 构造失败响应
         * 
         * @param <T>
         * @param message
         * @return
         */
        public static <T> RspWrapper<T> wrapFailResponse(String message) {
            RspWrapper<T> response = new RspWrapper<>();
            response.code = -1;
            response.message = message;
            return response;
        }
    }
}

package com.kaos.skynet.core.config.spring.exception.handler;

import com.kaos.skynet.core.config.spring.exception.ConversionException;
import com.kaos.skynet.core.config.spring.exception.util.ExceptionResponse;
import com.kaos.skynet.core.util.json.GsonWrapper;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Order(0)
@ControllerAdvice
class ConversionExceptionHandler {
    /**
     * 序列化工具
     */
    static GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 处理转换异常
     * 
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = ConversionException.class)
    public ExceptionResponse exceptionHandler(ConversionException ex) {
        return new ExceptionResponse() {
            @Override
            public Integer getCode() {
                return -1;
            }

            @Override
            public String getMessage() {
                return String.format("%s -> %s: %s", ex.getOrgType(), ex.getDstType(), ex.getMessage());
            }
        };
    }
}

package com.kaos.skynet.core.config.spring.converter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.kaos.skynet.core.util.json.GsonWrapper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * BODY处理器配置
 */
@Configuration
class HttpMessageConverterConfigurer implements WebMvcConfigurer {
    /**
     * Gson包装器
     */
    GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 注册HttpMessageConverter，用于读写Http消息的body
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 注册处理器
        converters.add(0, new JsonHttpMessageConverter());
        converters.add(0, new WorkBookHttpMessageConverter());
        converters.add(0, new BufferedImageHttpMessageConverter());

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }

    /**
     * application/json 处理器
     */
    class JsonHttpMessageConverter extends AbstractJsonHttpMessageConverter {
        @Override
        protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
            return gsonWrapper.fromJson(reader, resolvedType);
        }

        @Override
        protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
            // 包装body
            Map<String, Object> body = Maps.newHashMap();
            body.put("code", 0);
            body.put("data", object);

            // 序列化
            gsonWrapper.toJson(body, writer);
        }
    }

    /**
     * application/vnd.ms-excel 处理器
     */
    class WorkBookHttpMessageConverter extends AbstractHttpMessageConverter<Workbook> {
        /**
         * 构造函数
         */
        public WorkBookHttpMessageConverter() {
            super(new MediaType("application", "vnd.ms-excel", Charset.forName("UTF-8")));
        }

        @Override
        protected boolean supports(Class<?> clazz) {
            return Workbook.class.isAssignableFrom(clazz);
        }

        @Override
        protected Workbook readInternal(Class<? extends Workbook> clazz, HttpInputMessage inputMessage)
                throws IOException, HttpMessageNotReadableException {
            return null;
        }

        @Override
        protected void writeInternal(Workbook t, HttpOutputMessage outputMessage)
                throws IOException, HttpMessageNotWritableException {
            // 设置header
            var header = outputMessage.getHeaders();
            if (t instanceof HSSFWorkbook) {
                header.set("Content-Disposition", "attachment;filename=data.xls");
            } else {
                header.set("Content-Disposition", "attachment;filename=data.xlsx");
            }
            header.set("Pragma", "no-cache");
            header.set("Cache-Control", "no-cache");
            header.setExpires(0);

            // 设置body
            var body = outputMessage.getBody();
            t.write(body);
        }
    }
}

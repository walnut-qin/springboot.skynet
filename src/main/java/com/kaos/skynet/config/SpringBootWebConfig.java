package com.kaos.skynet.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.kaos.skynet.core.http.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.http.converter.DoubleHttpMessageConverter;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.string.date.StandardStringToDateConverter;
import com.kaos.skynet.core.type.converter.string.enums.factory.DescriptionStringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.string.local.date.StandardStringToLocalDateConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.StandardStringToLocalDateTimeConverter;
import com.kaos.skynet.core.type.converter.string.local.time.StandardStringToLocalTimeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring boot Web服务配置
 */
@Configuration
public class SpringBootWebConfig implements WebMvcConfigurer {
    @Autowired
    DescriptionStringToEnumConverterFactory descriptionStringToEnumConverterFactory;

    @Autowired
    StandardStringToDateConverter standardStringToDateConverter;

    @Autowired
    StandardStringToLocalDateConverter standardStringToLocalDateConverter;

    @Autowired
    StandardStringToLocalTimeConverter standardStringToLocalTimeConverter;

    @Autowired
    StandardStringToLocalDateTimeConverter standardStringToLocalDateTimeConverter;

    @Autowired
    BooleanHttpMessageConverter booleanHttpMessageConverter;

    @Autowired
    DoubleHttpMessageConverter doubleHttpMessageConverter;

    @Autowired
    JsonHttpMessageConverter jsonHttpMessageConverter;

    /**
     * 注册converter，用于解析Http请求参数
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 注册枚举解析器工厂
        registry.addConverterFactory(new ConverterFactory<String, Enum>() {
            @Override
            public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
                return new Converter<String, T>() {
                    @Override
                    public T convert(String source) {
                        return descriptionStringToEnumConverterFactory.getConverter(targetType).convert(source);
                    }
                };
            }
        });

        // 注册时间解析
        registry.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                return standardStringToDateConverter.convert(source);
            }
        });
        registry.addConverter(new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return standardStringToLocalDateConverter.convert(source);
            }
        });
        registry.addConverter(new Converter<String, LocalTime>() {
            @Override
            public LocalTime convert(String source) {
                return standardStringToLocalTimeConverter.convert(source);
            }
        });
        registry.addConverter(new Converter<String, LocalDateTime>() {
            @Override
            public LocalDateTime convert(String source) {
                return standardStringToLocalDateTimeConverter.convert(source);
            }
        });

        WebMvcConfigurer.super.addFormatters(registry);
    }

    /**
     * 注册HttpMessageConverter，用于读写Http消息的body
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 设置定制转换器，插入队列最前段，给予最高优先级
        converters.add(0, new BufferedImageHttpMessageConverter());
        converters.add(0, jsonHttpMessageConverter);
        converters.add(0, booleanHttpMessageConverter);
        converters.add(0, doubleHttpMessageConverter);

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}

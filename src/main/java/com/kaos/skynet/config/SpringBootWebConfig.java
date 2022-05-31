package com.kaos.skynet.config;

import java.util.List;

import com.kaos.skynet.core.http.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.http.converter.DoubleHttpMessageConverter;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.type.converter.string.date.StandardStringToDateConverter;
import com.kaos.skynet.core.type.converter.string.enums.DescriptionStringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.string.local.date.StandardStringToLocalDateConverter;
import com.kaos.skynet.core.type.converter.string.local.datime.StandardStringToLocalDateTimeConverter;
import com.kaos.skynet.core.type.converter.string.local.time.StandardStringToLocalTimeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
        registry.addConverterFactory(descriptionStringToEnumConverterFactory.transfer());

        // 注册时间解析
        registry.addConverter(standardStringToDateConverter.transfer());
        registry.addConverter(standardStringToLocalDateConverter.transfer());
        registry.addConverter(standardStringToLocalTimeConverter.transfer());
        registry.addConverter(standardStringToLocalDateTimeConverter.transfer());

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

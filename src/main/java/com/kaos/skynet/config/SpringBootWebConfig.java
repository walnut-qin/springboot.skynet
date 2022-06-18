package com.kaos.skynet.config;

import java.util.List;

import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.http.converter.WorkBookHttpMessageConverter;
import com.kaos.skynet.core.type.converter.StringToDateConverter;
import com.kaos.skynet.core.type.converter.StringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateTimeConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

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
    JsonHttpMessageConverter jsonHttpMessageConverter;

    @Autowired
    WorkBookHttpMessageConverter workBookHttpMessageConverter;

    /**
     * 注册converter，用于解析Http请求参数
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 注册枚举解析器工厂
        registry.addConverterFactory(new StringToEnumConverterFactory(false));

        // 注册时间解析
        registry.addConverter(new StringToDateConverter("yyyy-MM-dd HH:mm:ss"));
        registry.addConverter(new StringToLocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new StringToLocalTimeConverter("HH:mm:ss"));
        registry.addConverter(new StringToLocalDateTimeConverter("yyyy-MM-dd HH:mm:ss"));

        WebMvcConfigurer.super.addFormatters(registry);
    }

    /**
     * 注册HttpMessageConverter，用于读写Http消息的body
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 设置定制转换器，插入队列最前段，给予最高优先级
        converters.add(0, new BufferedImageHttpMessageConverter());
        converters.add(0, workBookHttpMessageConverter);
        converters.add(0, jsonHttpMessageConverter);

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}

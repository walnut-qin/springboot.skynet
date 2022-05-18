package com.kaos.skynet.config;

import java.util.List;

import com.kaos.skynet.core.gson.Gsons;
import com.kaos.skynet.core.http.message.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.http.message.converter.DoubleHttpMessageConverter;
import com.kaos.skynet.core.type.converter.string.date.StandardStringToLocalDateConverter;
import com.kaos.skynet.core.type.converter.string.datime.StandardStringToLocalDateTimeConverter;
import com.kaos.skynet.core.type.converter.string.enums.factory.DescriptionStringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.string.time.StandardStringToLocalTimeConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring boot Web服务配置
 */
@Configuration
public class SpringBootWebConfig implements WebMvcConfigurer {
    /**
     * 注册converter，用于解析Http请求参数
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 注册枚举解析器工厂
        registry.addConverterFactory(new DescriptionStringToEnumConverterFactory());

        // 注册时间解析
        registry.addConverter(new StandardStringToLocalDateConverter());
        registry.addConverter(new StandardStringToLocalTimeConverter());
        registry.addConverter(new StandardStringToLocalDateTimeConverter());

        WebMvcConfigurer.super.addFormatters(registry);
    }

    /**
     * 注册HttpMessageConverter，用于读写Http消息的body
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 设置定制转换器，插入队列最前段，给予最高优先级
        converters.add(0, new BufferedImageHttpMessageConverter());
        converters.add(0, new GsonHttpMessageConverter(Gsons.newGson()));
        converters.add(0, new BooleanHttpMessageConverter());
        converters.add(0, new DoubleHttpMessageConverter());

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}

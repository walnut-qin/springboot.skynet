package com.kaos.his.config;

import java.util.List;

import com.kaos.his.config.converter.DateTypeConverter;
import com.kaos.his.config.converter.StringTypeConverter;
import com.kaos.his.config.converter.factory.EnumTypeConverterFactory;
import com.kaos.his.config.message.converter.BooleanMessageConverter;
import com.kaos.his.config.message.converter.DoubleMessageConverter;
import com.kaos.his.util.Gsons;

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
        registry.addConverterFactory(new EnumTypeConverterFactory());

        // 注册时间解析
        registry.addConverter(new DateTypeConverter());

        // 注册字符串转换器
        registry.addConverter(new StringTypeConverter());

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
        converters.add(0, new BooleanMessageConverter());
        converters.add(0, new DoubleMessageConverter());

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}

package com.kaos.skynet.core.spring;

import java.util.List;

import com.kaos.skynet.core.spring.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.spring.converter.WorkBookHttpMessageConverter;
import com.kaos.skynet.core.spring.interceptor.LogInterceptor;
import com.kaos.skynet.core.spring.interceptor.TokenInterceptor;
import com.kaos.skynet.core.type.converter.StringToDateConverter;
import com.kaos.skynet.core.type.converter.StringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateTimeConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class SpringBootWebConfigurer implements WebMvcConfigurer {
    /**
     * 参数格式化工具
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 注册枚举解析器工厂
        registry.addConverterFactory(new StringToEnumConverterFactory(false));

        // 注册时间解析
        registry.addConverter(new StringToDateConverter("yyyy-MM-dd HH:mm:ss"));
        registry.addConverter(new StringToLocalDateTimeConverter("yyyy-MM-dd HH:mm:ss"));
        registry.addConverter(new StringToLocalDateConverter("yyyy-MM-dd"));
        registry.addConverter(new StringToLocalTimeConverter("HH:mm:ss"));

        WebMvcConfigurer.super.addFormatters(registry);
    }

    /**
     * 注册HttpMessageConverter，用于读写Http消息的body
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 图像转换器
        converters.add(0, new BufferedImageHttpMessageConverter());
        converters.add(0, new WorkBookHttpMessageConverter());
        converters.add(0, new JsonHttpMessageConverter());

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}

package com.kaos.his.config;

import java.util.List;

import com.kaos.helper.gson.converter.EnumTypeConverter;
import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.inf.IEnum;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring boot Web服务配置
 */
@Configuration
public class SpringBootWebConfig implements WebMvcConfigurer {
    /**
     * 自定义转换器（入参转换）
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加自定义工厂实体
        registry.addConverterFactory(new EnumTypeConverterFactory());

        WebMvcConfigurer.super.addFormatters(registry);
    }

    /**
     * 基于Gson枚举转换器的自定义转换器工厂
     */
    static class EnumTypeConverterFactory implements ConverterFactory<String, IEnum> {
        @Override
        public <T extends IEnum> Converter<String, T> getConverter(Class<T> targetType) {
            return new EnumTypeConverter<>(targetType);
        }
    }

    /**
     * body转换
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 清空原有转换器
        converters.clear();

        // 设置定制转换器
        converters.add(new GsonHttpMessageConverter(new GsonHelperImpl("yyyy-MM-dd HH:mm:ss").getGson()));

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}

package com.kaos.his.config;

import com.kaos.helper.gson.converter.EnumTypeConverter;
import com.kaos.inf.IEnum;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * spring boot Web服务配置
 */
@Configuration
public class SpringBootWebConfig implements WebMvcConfigurer {
    /**
     * 自定义转换器
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
}

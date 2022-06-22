package com.kaos.skynet.core.spring.formatter;

import com.kaos.skynet.core.type.converter.StringToDateConverter;
import com.kaos.skynet.core.type.converter.StringToEnumConverterFactory;
import com.kaos.skynet.core.type.converter.StringToLocalDateConverter;
import com.kaos.skynet.core.type.converter.StringToLocalDateTimeConverter;
import com.kaos.skynet.core.type.converter.StringToLocalTimeConverter;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * URL参数格式化处理器
 */
@Configuration
class FormatterConfigurer implements WebMvcConfigurer {
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
}

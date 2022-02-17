package com.kaos.his.config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.kaos.helper.gson.converter.EnumTypeConverter;
import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.inf.IEnum;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
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
     * 自定义转换器（入参转换）
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 添加自定义工厂实体
        registry.addConverterFactory(new EnumTypeConverterFactory());

        // 注入时间解析
        registry.addConverter(new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                // 判空
                if (source == null || source.isEmpty()) {
                    return null;
                }

                // 创建格式化工具
                var fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // 格式化
                try {
                    return fmt.parse(source);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("格式化Date参数失败(%s)", e.getMessage()));
                }
            }
        });

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
        // 设置定制转换器，插入队列最前段，给予最高优先级
        converters.add(0, new BufferedImageHttpMessageConverter());
        converters.add(0, new GsonHttpMessageConverter(new GsonHelperImpl("yyyy-MM-dd HH:mm:ss").getGson()));

        WebMvcConfigurer.super.extendMessageConverters(converters);
    }
}

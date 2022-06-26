package com.kaos.skynet.core.util.converter;

import java.text.DecimalFormat;

import com.kaos.skynet.core.config.spring.exception.ConversionException;
import com.kaos.skynet.core.util.StringUtils;

import org.springframework.core.convert.converter.Converter;

public class DoubleToStringConverter implements Converter<Double, String> {
    /**
     * 格式控制器
     */
    private DecimalFormat decimalFormat;

    /**
     * 构造函数
     * 
     * @param precision 精度
     */
    public DoubleToStringConverter(Integer precision) {
        this.decimalFormat = new DecimalFormat("#0.".concat(StringUtils.repeat('0', precision)));
    }

    @Override
    public String convert(Double source) {
        try {
            // 支持空值
            if (source == null) {
                return null;
            }

            // 执行转换
            return decimalFormat.format(source);
        } catch (Exception e) {
            throw new ConversionException(Double.class, String.class, e.getMessage());
        }
    }
}

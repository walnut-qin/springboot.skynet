package com.kaos.skynet.core.util.converter;

import java.time.Duration;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.core.convert.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DurationToStringConverter implements Converter<Duration, String> {
    /**
     * 年后缀
     */
    private String daySuffix;

    /**
     * 月后缀
     */
    private String hourSuffix;

    /**
     * 日后缀
     */
    private String minuteSuffix;

    /**
     * 日后缀
     */
    private String secondSuffix;

    /**
     * 是否缩略为0的值
     */
    private Boolean briefFlag;

    @Override
    public String convert(Duration source) {
        try {
            if (briefFlag) {
                return this.briefConvert(source);
            } else {
                return this.completeConvert(source);
            }
        } catch (Exception e) {
            throw new ConversionNotSupportedException(source, String.class, e);
        }
    }

    /**
     * 简略转换
     * 
     * @param source
     * @return
     */
    private String briefConvert(Duration source) {
        // 构造结果串
        String retStr = "";

        // 日
        var day = source.toDays();
        if (day != 0) {
            retStr += String.format("%d%s", day, daySuffix);
            source = source.minus(Duration.ofDays(day));
        }

        // 时
        var hour = source.toHours();
        if (hour != 0) {
            retStr += String.format("%d%s", hour, hourSuffix);
            source = source.minus(Duration.ofHours(hour));
        }

        // 分
        var minute = source.toMinutes();
        if (minute != 0) {
            retStr += String.format("%d%s", minute, minuteSuffix);
            source = source.minus(Duration.ofMinutes(minute));
        }

        // 秒
        var second = source.toSeconds();
        if (minute != 0) {
            retStr += String.format("%d%s", second, secondSuffix);
        }

        return retStr;
    }

    /**
     * 完整转换
     */
    private String completeConvert(Duration source) {
        // 构造结果串
        String retStr = "";

        // 日
        var day = source.toDays();
        retStr += String.format("%d%s", day, daySuffix);
        source = source.minus(Duration.ofDays(day));

        // 时
        var hour = source.toHours();
        retStr += String.format("%d%s", hour, hourSuffix);
        source = source.minus(Duration.ofHours(hour));

        // 分
        var minute = source.toMinutes();
        retStr += String.format("%d%s", minute, minuteSuffix);
        source = source.minus(Duration.ofMinutes(minute));

        // 秒
        var second = source.toSeconds();
        retStr += String.format("%d%s", second, secondSuffix);

        return retStr;
    }
}

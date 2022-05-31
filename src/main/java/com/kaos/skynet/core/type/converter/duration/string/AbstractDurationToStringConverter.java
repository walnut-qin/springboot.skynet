package com.kaos.skynet.core.type.converter.duration.string;

import java.time.Duration;

import com.kaos.skynet.core.type.converter.Converter;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractDurationToStringConverter extends Converter<Duration, String> {
    /**
     * 是否缩略为0的值
     */
    Boolean briefFlag = null;

    /**
     * 年后缀
     */
    String daySuffix = null;

    /**
     * 月后缀
     */
    String hourSuffix = null;

    /**
     * 日后缀
     */
    String minuteSuffix = null;

    /**
     * 日后缀
     */
    String secondSuffix = null;

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

    @Override
    public String convert(Duration source) {
        if (briefFlag) {
            return this.briefConvert(source);
        } else {
            return this.completeConvert(source);
        }
    }
}

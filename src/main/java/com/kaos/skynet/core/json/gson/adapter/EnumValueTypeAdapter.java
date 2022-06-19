package com.kaos.skynet.core.json.gson.adapter;

import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.converter.EnumToStringConverter;
import com.kaos.skynet.core.type.converter.StringToEnumConverterFactory;

/**
 * 反转枚举适配器，使用值域作为判断依据，因使用频繁，收入核心库
 */
public class EnumValueTypeAdapter<E extends Enum> extends EnumTypeAdapter<E> {
    /**
     * 构造函数
     */
    public EnumValueTypeAdapter() {
        super.rConverterFactory = new StringToEnumConverterFactory(true);
        super.wConverter = new EnumToStringConverter<>(true);
    }
}

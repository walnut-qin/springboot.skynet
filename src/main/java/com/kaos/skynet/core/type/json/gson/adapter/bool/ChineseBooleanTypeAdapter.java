package com.kaos.skynet.core.type.json.gson.adapter.bool;

import com.kaos.skynet.core.type.converter.bool.string.ChineseBooleanToStringConverter;
import com.kaos.skynet.core.type.converter.string.bool.ChineseStringToBooleanConverter;

public class ChineseBooleanTypeAdapter extends AbstractBooleanTypeAdapter {
    public ChineseBooleanTypeAdapter() {
        super(new ChineseBooleanToStringConverter(), new ChineseStringToBooleanConverter());
    }
}

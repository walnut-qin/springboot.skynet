package com.kaos.skynet.core.json.gson.adapter;

import com.kaos.skynet.core.type.converter.BooleanToStringConverter;
import com.kaos.skynet.core.type.converter.StringToBooleanConverter;

public class BooleanChineseTypeAdapter extends BooleanTypeAdapter {
    public BooleanChineseTypeAdapter() {
        super(new StringToBooleanConverter("是", "否"), new BooleanToStringConverter("是", "否"));
    }
}

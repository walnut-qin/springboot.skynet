package com.kaos.helper.holiday.impl;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import com.kaos.helper.gson.GsonHelper;
import com.kaos.helper.gson.adapter.EnumTypeAdapter;
import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.helper.holiday.HolidayManager;
import com.kaos.helper.holiday.entity.*;
import com.kaos.helper.holiday.enums.CodeEnum;
import com.kaos.helper.holiday.enums.DayTypeEnum;
import com.kaos.helper.holiday.enums.WeekEnum;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HolidayManagerImpl implements HolidayManager {
    /**
     * HTTP句柄
     */
    RestTemplate restTemplate = new RestTemplate();

    /**
     * 构造函数
     */
    public HolidayManagerImpl() {
        GsonHelper gsonHelper = new GsonHelperImpl("yyyy-MM-dd", new HashMap<Type, Object>() {
            {
                put(CodeEnum.class, new EnumTypeAdapter<>(CodeEnum.class));
                put(DayTypeEnum.class, new EnumTypeAdapter<>(DayTypeEnum.class));
                put(WeekEnum.class, new EnumTypeAdapter<>(WeekEnum.class));
            }
        });

        // 重置json解析器
        this.restTemplate.getMessageConverters().clear();
        this.restTemplate.getMessageConverters().add(new GsonHttpMessageConverter(gsonHelper.getGson()));
    }

    /**
     * 获取节假日信息
     */
    @Override
    public DayInfo getDayInfo(Date date) {
        // 初始化时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 构造请求url
        String url = String.format("http://timor.tech/api/holiday/info/%s", formatter.format(date));

        // 发送web请求
        return this.restTemplate.getForObject(url, DayInfo.class);
    }
}

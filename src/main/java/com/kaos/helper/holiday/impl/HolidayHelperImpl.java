package com.kaos.helper.holiday.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.kaos.helper.gson.GsonHelper;
import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.helper.holiday.HolidayHelper;
import com.kaos.helper.holiday.entity.*;

import org.apache.log4j.Logger;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HolidayHelperImpl implements HolidayHelper {
    /**
     * 日志工具
     */
    Logger logger = Logger.getLogger(HolidayHelperImpl.class.getName());

    /**
     * GsonHelper实体
     */
    GsonHelper gsonHelper = new GsonHelperImpl("yyyy-MM-dd");

    /**
     * HTTP句柄
     */
    RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
        {
            add(new GsonHttpMessageConverter(HolidayHelperImpl.this.gsonHelper.getGson()));
        }
    });

    /**
     * 获取节假日信息
     */
    @Override
    public DayInfo getDayInfo(Date date) {
        // 初始日志
        this.logger.debug(String.format("获取节假日信息", date.toString()));

        // 初始化时间格式化工具
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // 构造请求url
        String url = String.format("http://timor.tech/api/holiday/info/%s", formatter.format(date));

        // 发送web请求
        var dayInfo = this.restTemplate.getForObject(url, DayInfo.class);
        this.logger.debug(String.format("响应实体 = %s", this.gsonHelper.toJson(dayInfo)));

        return dayInfo;
    }
}

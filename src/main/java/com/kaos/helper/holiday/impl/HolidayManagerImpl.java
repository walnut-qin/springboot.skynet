package com.kaos.helper.holiday.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.kaos.helper.holiday.HolidayManager;
import com.kaos.helper.holiday.entity.*;

import org.springframework.web.client.RestTemplate;

public class HolidayManagerImpl implements HolidayManager {
    /**
     * HTTP句柄
     */
    RestTemplate restTemplate = new RestTemplate();

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

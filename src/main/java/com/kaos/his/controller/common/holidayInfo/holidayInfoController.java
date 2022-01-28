package com.kaos.his.controller.common.holidayInfo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.kaos.helper.holiday.HolidayHelper;
import com.kaos.helper.holiday.entity.DayInfo;
import com.kaos.helper.holiday.impl.HolidayHelperImpl;

import org.apache.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/holidayinfo")
public class holidayInfoController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(holidayInfoController.class.getName());

    /**
     * 节假日助手
     */
    HolidayHelper holidayHelper = new HolidayHelperImpl();

    /**
     * 查询节假日信息
     * 
     * @param date
     * @return
     */
    @RequestMapping(value = "queryHolidayInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public DayInfo queryHolidayInfo(@NotNull(message = "日期不能为空") Date date) {
        // 记录日志
        this.logger.info(String.format("查询节假日信息(date = %s)", date.toString()));

        // 调用服务
        return this.holidayHelper.getDayInfo(date);
    }
}
package com.kaos.skynet.api.logic.controller.common;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.http.RspWrapper;
import com.kaos.skynet.core.json.Json;
import com.kaos.skynet.plugin.timor.TimorPlugin;
import com.kaos.skynet.plugin.timor.enums.DayTypeEnum;
import com.kaos.skynet.plugin.timor.enums.WeekEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("api/common/holiday")
public class HolidayController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * Timor插件
     */
    @Autowired
    TimorPlugin timorPlugin;

    /**
     * 查询节假日信息
     * 
     * @param date
     * @return
     */
    @RequestMapping(value = "getDayInfo", method = RequestMethod.GET, produces = MediaType.JSON)
    public RspWrapper<GetDayInfo.RspBody> getDayInfo(@NotNull(message = "日期不能为空") LocalDate date) {
        try {
            // 记录日志
            log.info(String.format("查询节假日信息(date = %s)", json.toJson(date)));

            // 获取节假日信息
            var holidayInfo = timorPlugin.getDayInfo(date);
            if (holidayInfo == null) {
                log.error("未从服务器获取到节假日信息");
                throw new RuntimeException("未从服务器获取到节假日信息");
            }

            // 构造响应
            var rspBuilder = GetDayInfo.RspBody.builder();
            rspBuilder.type(holidayInfo.getType().getType());
            rspBuilder.name(holidayInfo.getType().getName());
            rspBuilder.week(holidayInfo.getType().getWeek());
            return RspWrapper.wrapSuccessResponse(rspBuilder.build());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    @Getter
    private static class GetDayInfo {
        @Getter
        @Builder
        static class RspBody {
            /**
             * 日期类型
             */
            private DayTypeEnum type;

            /**
             * 类型中文名
             */
            private String name;

            /**
             * 星期
             */
            private WeekEnum week;
        }
    }
}

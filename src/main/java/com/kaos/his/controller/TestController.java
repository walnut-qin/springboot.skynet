package com.kaos.his.controller;

import java.util.Date;

import com.kaos.his.service.DayBalanceService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class TestController {
    @Autowired
    DayBalanceService dayBalanceService;

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String Run(@RequestParam("operCode") String operCode, @RequestParam("beginDate") Date beginDate,
            @RequestParam("endDate") Date endDate) {
        var v = this.dayBalanceService.QueryDayBalanceData(operCode, beginDate, endDate);

        return GsonHelper.ToJson(v);
    }
}

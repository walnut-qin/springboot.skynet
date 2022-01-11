package com.kaos.his.controller.dayreport;

import com.kaos.his.service.DayReportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class DayReportController {
    /**
     * 接口：日志服务
     */
    Logger logger = Logger.getLogger(DayReportController.class.getName());

    /**
     * 接口：手术服务
     */
    @Autowired
    DayReportService dayReportService;

    @ResponseBody
    @RequestMapping(value = "dayreport/fixNewYbData", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String fixNewYbData(@RequestParam("statNo") String statNo) {
        // 入参判断
        if (statNo == null || statNo.isEmpty()) {
            throw new RuntimeException("statNo不能为空");
        }

        // 记录日志
        this.logger.info(String.format("修改新医保日结数据(statNo = %s)", statNo));

        // 调用服务
        this.dayReportService.fixNewYbDayReportData(statNo);

        return "修改完毕";
    }
}

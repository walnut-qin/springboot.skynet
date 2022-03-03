package com.kaos.his.controller.impl.inpatient.fee.balance.report;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.inpatient.fee.balance.report.DayReportController;
import com.kaos.his.service.inf.inpatient.fee.report.ReportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping({ "/ms/inpatient/fee/balance/report", "/ms/inpatient/dayreport" })
public class ReportControllerImpl implements DayReportController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ReportControllerImpl.class);

    /**
     * 业务接口
     */
    @Autowired
    ReportService dayReportService;

    @Override
    @RequestMapping(value = "queryNewYbPubCost", method = RequestMethod.GET, produces = MediaType.TEXT)
    public Double queryNewYbPubCost(@NotNull(message = "结算员不能为空") String balancer,
            @NotNull(message = "开始时间不能为空") Date beginDate,
            @NotNull(message = "结束时间不能为空") Date endDate) {
        // 查询统筹
        return this.dayReportService.queryNewYbPubCost(balancer, beginDate, endDate);
    }

    @Override
    @RequestMapping(value = "queryNewYbPayCost", method = RequestMethod.GET, produces = MediaType.TEXT)
    public Double queryNewYbPayCost(@NotNull(message = "结算员不能为空") String balancer,
            @NotNull(message = "开始时间不能为空") Date beginDate,
            @NotNull(message = "结束时间不能为空") Date endDate) {
        // 查询账户
        return this.dayReportService.queryNewYbPayCost(balancer, beginDate, endDate);
    }

    @Override
    @RequestMapping(value = "fixNewYbData", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String fixNewYbData(@NotNull(message = "日结编码不能为空") String statNo) {
        // 调用服务
        this.dayReportService.fixNewYbDayReportData(statNo);

        return "修改完毕";
    }

    @Override
    @RequestMapping(value = "fixNewYbDataInDeptOwn", method = RequestMethod.POST, produces = MediaType.TEXT)
    public String fixNewYbDataInDeptOwn(@RequestBody @Valid FixInDeptOwnReq req) {
        // 调用服务
        this.dayReportService.fixNewYbDayReportData(req.beginDate, req.endDate, req.deptOwn);

        return "修改完毕";
    }
}

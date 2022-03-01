package com.kaos.his.controller.impl.inpatient.fee.balance.report;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.inpatient.fee.balance.report.DayReportController;
import com.kaos.his.service.inf.inpatient.fee.report.DayReportService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping({ "/ms/inpatient/fee/balance/report/info", "/ms/inpatient/dayreport" })
public class DayReportControllerImpl implements DayReportController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DayReportControllerImpl.class);

    /**
     * 业务接口
     */
    @Autowired
    DayReportService dayReportService;

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

    @Override
    @RequestMapping(value = "exportNewYbData", method = RequestMethod.POST, produces = MediaType.JSON)
    public ExportNewYbDataRsp exportNewYbData(@RequestBody @Valid ExportNewYbDataReq req) {
        // 调用服务
        var data = this.dayReportService.exportNewYbData(req.beginDate, req.endDate, req.deptOwn);

        // 创建响应
        ExportNewYbDataRsp rsp = new ExportNewYbDataRsp();
        rsp.newYb = new ExportNewYbDataRsp.NewYb();
        rsp.newYb.pubCost = data.getValue1().getValue0();
        rsp.newYb.payCost = data.getValue1().getValue1();
        rsp.otherYb = new ExportNewYbDataRsp.OtherYb();
        rsp.otherYb.pubCost = data.getValue2().getValue0();
        rsp.otherYb.payCost = data.getValue2().getValue1();
        rsp.data = data.getValue0().asMap();

        return rsp;
    }
}

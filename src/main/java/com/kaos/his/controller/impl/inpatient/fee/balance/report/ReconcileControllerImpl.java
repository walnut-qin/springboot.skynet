package com.kaos.his.controller.impl.inpatient.fee.balance.report;

import javax.validation.Valid;

import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.kaos.his.controller.MediaType;
import com.kaos.his.controller.inf.inpatient.fee.balance.report.ReconcileController;
import com.kaos.his.service.inf.inpatient.fee.report.ReconcileService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ms/inpatient/fee/balance/report/reconcile")
public class ReconcileControllerImpl implements ReconcileController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(ReconcileControllerImpl.class);

    /**
     * 业务接口
     */
    @Autowired
    ReconcileService reconcileService;

    @Override
    @RequestMapping(value = "check", method = RequestMethod.POST, produces = MediaType.JSON)
    public CheckRsp check(@RequestBody @Valid CheckReq req) {
        // 调用业务
        var data = this.reconcileService.checkInpatientIncome(req.beginDate, req.endDate);

        // 构造响应体
        CheckRsp rsp = new CheckRsp();
        rsp.size = data.size();
        rsp.data = Maps.newHashMap();
        for (var dept : data.keySet()) {
            var mp = data.get(dept);

            var innerData = new CheckRsp.Data();
            innerData.size = mp.size();
            innerData.data = Maps.newHashMap();
            for (var recipeNo : mp.keySet()) {
                var mp2 = mp.get(recipeNo);

                var innerSubData = new CheckRsp.Data.SubData();
                innerSubData.feeInfo = mp2.getValue0();
                innerSubData.itemList = mp2.getValue1();
                innerSubData.medList = mp2.getValue2();

                innerData.data.put(recipeNo, innerSubData);
            }

            rsp.data.put(dept, innerData);
        }

        return rsp;
    }

    @Override
    @RequestMapping(value = "exportNewYbData", method = RequestMethod.POST, produces = MediaType.JSON)
    public ExportNewYbDataRsp exportNewYbData(ExportNewYbDataReq req) {
        // 执行业务
        var data = this.reconcileService.exportNewYbData(req.beginDate, req.endDate, req.deptOwn);
        if (data == null) {
            return null;
        }

        // 构造响应
        ExportNewYbDataRsp rsp = new ExportNewYbDataRsp();
        rsp.size = data.size();
        rsp.data = Maps.newTreeMap(Ordering.natural());
        for (var key : data.keySet()) {
            rsp.data.put(key, new ExportNewYbDataRsp.Data());
            rsp.data.get(key).costData = new ExportNewYbDataRsp.Data.CostData();
            rsp.data.get(key).costData.pubCost = data.get(key).getValue0().getValue0();
            rsp.data.get(key).costData.payCost = data.get(key).getValue0().getValue1();
            rsp.data.get(key).detail = data.get(key).getValue1().asMap();
        }

        return rsp;
    }
}

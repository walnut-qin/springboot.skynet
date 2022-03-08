package com.kaos.skynet.api.controller.impl.inpatient.fee;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.inf.inpatient.fee.PrepayController;
import com.kaos.skynet.api.service.inf.inpatient.fee.PrepayService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/fee/prepay")
public class PrepayControllerImpl implements PrepayController {
    /**
     * 日志
     */
    Logger logger = Logger.getLogger(PrepayControllerImpl.class);

    /**
     * 预交金业务
     */
    @Autowired
    PrepayService prepayService;

    @Override
    @RequestMapping(value = "fixPrepayCost", method = RequestMethod.GET, produces = MediaType.JSON)
    public FixPrepayCostRsp fixPrepayCost(String patientNo) {
        // 记录日志
        this.logger.info(String.format("修改预交金, 住院号 = %s", patientNo));

        // 调用业务
        var ret = this.prepayService.fixPrepayCost(patientNo);
        if (ret == null) {
            return null;
        }

        // 构造响应
        FixPrepayCostRsp rsp = new FixPrepayCostRsp();
        rsp.patientNo = patientNo;
        rsp.size = ret.size();
        rsp.modifiedDatas = Lists.newArrayList();
        for (var key : ret.keySet()) {
            // 获取元素
            var costs = ret.get(key);
            // 构造元素
            var modifiedData = new FixPrepayCostRsp.ModifiedData();
            modifiedData.happenNo = key;
            modifiedData.orgPrepayCost = costs.getValue0();
            modifiedData.dstPrepayCost = costs.getValue1();
            rsp.modifiedDatas.add(modifiedData);
        }
        return rsp;
    }
}

package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic;

import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoiceMapper;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.json.Json;
import com.kaos.skynet.core.json.gson.adapter.enums.ValueEnumTypeAdapter;
import com.kaos.skynet.plugin.bosoft.BoSoftPlugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/api/inpatient/fee/balance/invoice/electronic")
public class ElectronicInvoiceController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 电子发票表接口
     */
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    /**
     * 服务器处理器
     */
    @Autowired
    BoSoftPlugin boSoftPlugin;

    @RequestMapping(value = "queryElectronicInvoiceInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    public Object queryElectronicInvoiceInfo(@RequestBody QueryElectronicInvoiceInfo.ReqBody reqBody) {
        // 记录日志
        log.info(String.format("获取发票信息, reqBody = %s", json.toJson(reqBody)));

        // 查询电子发票记录
        var invoice = electronicInvoiceMapper.queryInvoice(reqBody.getInvoiceNo(), reqBody.getTransType());
        if (invoice == null) {
            throw new RuntimeException("不存在的电子发票");
        }

        // 发送请求
        return boSoftPlugin.postForObject("getEBillByBusNo",
                new QueryElectronicInvoiceInfo.BoSoftReqBody(invoice.getBusNo()),
                QueryElectronicInvoiceInfo.BoSoftRspBody.class);
    }

    /**
     * 接口Body
     */
    public static class QueryElectronicInvoiceInfo {
        /**
         * 请求body
         */
        @Getter
        public static class ReqBody {
            /**
             * HIS发票号
             */
            private String invoiceNo;

            /**
             * 交易类型
             */
            @JsonAdapter(ValueEnumTypeAdapter.class)
            private TransTypeEnum transType;
        }

        @Getter
        public static class BoSoftReqBody {
            /**
             * 业务流水号
             */
            private String busNo;

            /**
             * 业务发生时间
             */
            private String busDateTime;

            /**
             * 单参数构造
             * 
             * @param busNo
             */
            public BoSoftReqBody(String busNo) {
                this.busNo = busNo;
                this.busDateTime = busNo.split("-")[0];
            }
        }

        @Getter
        public static class BoSoftRspBody {
            /**
             * 电子票据代码
             */
            private String billBatchCode;

            /**
             * 电子票据号码
             */
            private String billNo;

            /**
             * 电子校验码
             */
            private String random;
        }
    }
}

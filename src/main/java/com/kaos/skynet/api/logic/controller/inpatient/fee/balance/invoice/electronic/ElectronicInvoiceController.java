package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic;

import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoiceMapper;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.json.adapter.EnumTypeAdapter_Value;
import com.kaos.skynet.core.spring.converter.JsonWrappedHttpMessageConverter.RspWrapper;
import com.kaos.skynet.core.spring.interceptor.LogInterceptor.ApiName;
import com.kaos.skynet.plugin.bosoft.BoSoftPlugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/inpatient/fee/balance/invoice/electronic")
class ElectronicInvoiceController {
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

    @ApiName("获取发票信息")
    @RequestMapping(value = "queryElectronicInvoiceInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    RspWrapper<Object> queryElectronicInvoiceInfo(@RequestBody QueryElectronicInvoiceInfo.ReqBody reqBody) {
        try {
            // 查询电子发票记录
            var invoice = electronicInvoiceMapper.queryInvoice(reqBody.invoiceNo, reqBody.transType);
            if (invoice == null) {
                throw new RuntimeException("不存在的电子发票");
            }

            // 发送请求
            return RspWrapper.wrapSuccessResponse(
                    boSoftPlugin.postForObject("getEBillByBusNo",
                            new QueryElectronicInvoiceInfo.BoSoftReqBody(invoice.getBusNo()),
                            QueryElectronicInvoiceInfo.BoSoftRspBody.class));
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    /**
     * 接口Body
     */
    static class QueryElectronicInvoiceInfo {
        /**
         * 请求body
         */
        static class ReqBody {
            /**
             * HIS发票号
             */
            String invoiceNo;

            /**
             * 交易类型
             */
            @JsonAdapter(EnumTypeAdapter_Value.class)
            TransTypeEnum transType;
        }

        public static class BoSoftReqBody {
            /**
             * 业务流水号
             */
            String busNo;

            /**
             * 业务发生时间
             */
            String busDateTime;

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

        static class BoSoftRspBody {
            /**
             * 电子票据代码
             */
            String billBatchCode;

            /**
             * 电子票据号码
             */
            String billNo;

            /**
             * 电子校验码
             */
            String random;
        }
    }
}

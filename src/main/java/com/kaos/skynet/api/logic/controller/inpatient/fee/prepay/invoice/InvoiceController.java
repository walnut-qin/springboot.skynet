package com.kaos.skynet.api.logic.controller.inpatient.fee.prepay.invoice;

import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.api.data.his.mapper.common.fee.invoice.FinComElectronicInvoiceMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.fee.FinIpbInPrepayMapper;
import com.kaos.skynet.api.logic.service.inpatient.fee.prepay.invoice.InvoiceService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.ObjectUtils;
import com.kaos.skynet.core.util.thread.lock.LockExecutor;
import com.kaos.skynet.core.util.thread.lock.LockGuardian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/inpatient/fee/prepay/invoice")
public class InvoiceController {
    /**
     * 锁
     */
    LockGuardian invoiceLockGuardian = new LockGuardian("电子发票中间表锁", 50);

    /**
     * 预交金接口
     */
    @Autowired
    FinIpbInPrepayMapper prepayMapper;

    /**
     * 电子发票接口
     */
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    /**
     * 电子发票预交金业务
     */
    @Autowired
    InvoiceService invoiceService;

    /**
     * 隔日召回修改预交金
     * 
     * @param reqBody
     * @return
     */
    @ApiName("获取预交金电子票据信息")
    @RequestMapping(value = "getPrepayInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    List<GetPrepayInfo.RspBody> getPrepayInfo(@RequestBody @Valid GetPrepayInfo.ReqBody reqBody) {
        // 检索预交金
        var keyBuilder = FinIpbInPrepayMapper.Key.builder();
        keyBuilder.inpatientNo("ZY01".concat(reqBody.patientNo));
        var prepays = prepayMapper.queryPrepays(keyBuilder.build());

        var rspBuilder = GetPrepayInfo.RspBody.builder();
        return prepays.stream().map(x -> {
            // 检索
            var electronicInvoice = electronicInvoiceMapper.queryInvoice(x.getReceiptNo(), TransTypeEnum.Positive);
            if (electronicInvoice == null) {
                return null;
            }

            // 构造响应
            rspBuilder.receiptNo(x.getReceiptNo());
            rspBuilder.billBatchNo(electronicInvoice.getBillBatchCode());
            rspBuilder.billNo(electronicInvoice.getBillNo());
            rspBuilder.random(electronicInvoice.getRandom());
            return rspBuilder.build();
        }).filter(Objects::nonNull).toList();
    }

    /**
     * fixRecallPrepay的请求和响应body
     */
    static class GetPrepayInfo {
        /**
         * 请求body
         */
        static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "住院号不能为空")
            String patientNo;
        }

        /**
         * 响应body
         */
        @Builder
        static class RspBody {
            /**
             * 住院流水号
             */
            String receiptNo;

            /**
             * 预交金序号
             */
            String billBatchNo;

            /**
             * 原值
             */
            String billNo;

            /**
             * 新值
             */
            String random;
        }
    }

    /**
     * 隔日召回修改预交金
     * 
     * @param reqBody
     * @return
     */
    @ApiName("修复预交金电子票据信息")
    @RequestMapping(value = "fixPrepayInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    Object fixPrepayInfo(@RequestBody @Valid FixPrepayInfo.ReqBody reqBody) {
        LockExecutor.execute(Lists.newArrayList(invoiceLockGuardian.grant(reqBody.receiptNo)), () -> {
            invoiceService.fixPrepayElectronicInvoiceInfo(reqBody.receiptNo);
        });

        return ObjectUtils.emptyObject;
    }

    /**
     * fixRecallPrepay的请求和响应body
     */
    static class FixPrepayInfo {
        /**
         * 请求body
         */
        static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "预交金票据号不能为空")
            String receiptNo;
        }
    }
}

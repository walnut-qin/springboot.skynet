package com.kaos.skynet.api.service.impl.inpatient.fee.balance.invoice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kaos.skynet.api.service.inf.inpatient.fee.balance.invoice.ElectronicInvoiceService;
import com.kaos.skynet.entity.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoice;
import com.kaos.skynet.enums.impl.common.SourceTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic.BusinessTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic.PlaceCodeEnum;

import lombok.Data;

public class ElectronicInvoiceServiceImpl implements ElectronicInvoiceService {
    @Override
    public FinComElectronicInvoice register(String invoiceNo, SourceTypeEnum sourceType, String alipayCode,
            String weChatOrderNo, String openId) {
        return null;
    }

    @Data
    private static class RegisterReqBody {
        /**
         * 业务流水号
         */
        private String busNo = null;

        /**
         * 业务类型 {@link BusinessTypeEnum#getValue()}
         */
        private String busType = null;

        /**
         * 患者姓名
         */
        private String payer = null;

        /**
         * 业务发生时间
         * {@link LocalDateTime#format(DateTimeFormatter)}
         * {@link DateTimeFormatter#ofPattern(String)}
         * {@value "yyyyMMddHHmmssfff"}
         */
        private String busDateTime = null;

        /**
         * 开票点编码
         */
        private PlaceCodeEnum placeCode = null;
    }
}

package com.kaos.skynet.api.service.inf.inpatient.fee.balance.invoice;

import com.kaos.skynet.api.data.entity.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoice;
import com.kaos.skynet.api.data.enums.SourceTypeEnum;

public interface ElectronicInvoiceService {
    /**
     * 开具结算电子发票
     */
    FinComElectronicInvoice register(String invoiceNo, SourceTypeEnum sourceType, String alipayCode,
            String weChatOrderNo, String openId);
}

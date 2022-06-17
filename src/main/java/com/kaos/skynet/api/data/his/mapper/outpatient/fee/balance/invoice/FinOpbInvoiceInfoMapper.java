package com.kaos.skynet.api.data.his.mapper.outpatient.fee.balance.invoice;

import com.kaos.skynet.api.data.his.entity.outpatient.fee.balance.invoice.FinOpbInvoiceInfo;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

public interface FinOpbInvoiceInfoMapper {
    /**
     * 检索发票信息
     * 
     * @param invoiceNo  发票号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType  交易类型, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param invoiceSeq 发票序号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinOpbInvoiceInfo queryInvoiceInfo(String invoiceNo, TransTypeEnum transType, String invoiceSeq);
}

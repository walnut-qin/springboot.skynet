package com.kaos.his.mapper.outpatient.fee.balance;

import com.kaos.his.entity.outpatient.fee.balance.FinOpbInvoiceInfo;
import com.kaos.his.enums.impl.common.TransTypeEnum;

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

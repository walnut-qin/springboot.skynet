package com.kaos.skynet.api.mapper.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.entity.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoice;
import com.kaos.skynet.enums.common.TransTypeEnum;

public interface FinComElectronicInvoiceMapper {
    /**
     * 主键查询
     * 
     * @param invoiceNo HIS发票号, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @param transType 交易类型, 等于 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    FinComElectronicInvoice queryInvoice(String invoiceNo, TransTypeEnum transType);

    /**
     * 插入一条发票记录
     * 
     * @param invoice 待插入的发票实体
     * @return 受影响的行数
     */
    Integer insertInvoice(FinComElectronicInvoice invoice);

    /**
     * 修改发票记录
     * 
     * @param invoice 修改的发票实体
     * @return 受影响的行数
     */
    Integer updateInvoice(FinComElectronicInvoice invoice);
}

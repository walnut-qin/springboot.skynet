package com.kaos.skynet.api.data.his.mapper.common.fee.invoice;

import com.kaos.skynet.api.data.his.entity.common.fee.invoice.FinComElectronicInvoice;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

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

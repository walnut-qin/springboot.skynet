package com.kaos.skynet.api.mapper.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.entity.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoice;
import com.kaos.skynet.enums.impl.common.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComElectronicInvoiceMapperTests {
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    @Test
    public void queryInvoice() {
        this.electronicInvoiceMapper.queryInvoice("000000017311", TransTypeEnum.Positive);
    }
}

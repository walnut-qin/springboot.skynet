package com.kaos.skynet.api.data.mapper.inpatient.fee.balance.invoice.electronic;

import com.kaos.skynet.api.enums.common.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComElectronicInvoiceMapperTests {
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    @Test
    void queryInvoice() {
        electronicInvoiceMapper.queryInvoice("200917009430", TransTypeEnum.Positive);
    }
}

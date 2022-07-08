package com.kaos.skynet.api.data.his.mapper.common.fee.invoice;

import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

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

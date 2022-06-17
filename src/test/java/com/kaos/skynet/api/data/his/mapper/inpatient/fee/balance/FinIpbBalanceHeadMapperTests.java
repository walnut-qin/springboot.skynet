package com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance;

import com.kaos.skynet.api.data.his.enums.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbBalanceHeadMapperTests {
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    @Test
    void queryBalanceHead() {
        balanceHeadMapper.queryBalanceHead("invoiceNo", TransTypeEnum.Positive);
    }

    @Test
    void queryBalanceHeads() {
        balanceHeadMapper.queryBalanceHeads(FinIpbBalanceHeadMapper.Key.builder()
                .inpatientNo("inpatientNo")
                .build());
    }
}

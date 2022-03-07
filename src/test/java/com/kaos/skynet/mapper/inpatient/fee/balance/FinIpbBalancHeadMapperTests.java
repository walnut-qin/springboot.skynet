package com.kaos.skynet.mapper.inpatient.fee.balance;

import java.util.Date;

import com.kaos.skynet.enums.impl.common.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbBalancHeadMapperTests {
    @Autowired
    FinIpbBalanceHeadMapper finIpbBalanceHeadMapper;

    @Test
    public void queryBalanceHead() {
        this.finIpbBalanceHeadMapper.queryBalance("000001914165", TransTypeEnum.Positive);
    }

    @Test
    public void queryBalanceHeadsInBalancer() {
        this.finIpbBalanceHeadMapper.queryBalancesInBalancer("006017", new Date(), new Date(), "18");
    }

    @Test
    public void queryBalanceHeadsInDayReport() {
        this.finIpbBalanceHeadMapper.queryBalancesInDayReport(null, null);
        this.finIpbBalanceHeadMapper.queryBalancesInDayReport("163411", null);
    }
}

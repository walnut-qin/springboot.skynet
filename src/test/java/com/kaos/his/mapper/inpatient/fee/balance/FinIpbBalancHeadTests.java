package com.kaos.his.mapper.inpatient.fee.balance;

import java.util.Date;

import com.kaos.his.enums.common.TransTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbBalancHeadTests {
    @Autowired
    FinIpbBalanceHeadMapper finIpbBalanceHeadMapper;

    @Test
    public void queryBalanceHead() {
        this.finIpbBalanceHeadMapper.queryBalanceHead("000001914165", TransTypeEnum.Positive);
    }

    @Test
    public void queryBalanceHeadsInBalancer() {
        this.finIpbBalanceHeadMapper.queryBalanceHeadsInBalancer("006017", new Date(), new Date(), "18");
    }

    @Test
    public void queryBalanceHeadsInDayReport() {
        this.finIpbBalanceHeadMapper.queryBalanceHeadsInDayReport(null);
        this.finIpbBalanceHeadMapper.queryBalanceHeadsInDayReport("163411");
    }
}

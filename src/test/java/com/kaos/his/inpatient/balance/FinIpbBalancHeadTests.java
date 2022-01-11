package com.kaos.his.inpatient.balance;

import java.util.Date;

import com.kaos.his.enums.TransTypeEnum;
import com.kaos.his.mapper.inpatient.balance.FinIpbBalanceHeadMapper;

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
}

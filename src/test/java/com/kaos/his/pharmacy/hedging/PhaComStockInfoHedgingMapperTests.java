package com.kaos.his.pharmacy.hedging;

import com.kaos.his.mapper.pharmacy.hedging.PhaComStockInfoHedgingMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PhaComStockInfoHedgingMapperTests {
    @Autowired
    PhaComStockInfoHedgingMapper phaComStockInfoHedgingMapper;

    @Test
    public void queryStockInfo() {
        this.phaComStockInfoHedgingMapper.queryStockInfo("1240", null);
        this.phaComStockInfoHedgingMapper.queryStockInfo("1240", "Y00000016786");
    }
}

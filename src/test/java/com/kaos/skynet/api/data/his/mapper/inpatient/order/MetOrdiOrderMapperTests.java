package com.kaos.skynet.api.data.his.mapper.inpatient.order;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOrdiOrderMapperTests {
    @Autowired
    MetOrdiOrderMapper metOrdiOrderMapper;

    @Test
    void queryOrder() {
        metOrdiOrderMapper.queryOrder("0123456789");
    }

    @Test
    void queryOrders() {
        metOrdiOrderMapper.queryOrders(MetOrdiOrderMapper.Key.builder()
                .inpatientNo("ZY01000")
                .build());
    }
}

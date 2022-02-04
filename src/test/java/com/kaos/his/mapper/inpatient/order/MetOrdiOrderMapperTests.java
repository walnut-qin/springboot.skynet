package com.kaos.his.mapper.inpatient.order;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOrdiOrderMapperTests {
    @Autowired
    MetOrdiOrderMapper metOrdiOrderMapper;

    @Test
    public void queryInpatientOrder() {
        this.metOrdiOrderMapper.queryInpatientOrder("moOrder");
    }

    @Test
    public void queryInpatientOrders() {
        this.metOrdiOrderMapper.queryInpatientOrders("inpatientNo", "termId", new Date(), new Date());
    }
}

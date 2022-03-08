package com.kaos.skynet.api.mapper.inpatient.fee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbInPrepayMapperTests {
    @Autowired
    FinIpbInPrepayMapper mapper;

    @Test
    public void queryPrepays() {
        this.mapper.queryPrepays(null);
        this.mapper.queryPrepays("ZY010000706272");
    }
}

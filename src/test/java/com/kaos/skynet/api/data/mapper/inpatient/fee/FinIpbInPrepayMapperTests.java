package com.kaos.skynet.api.data.mapper.inpatient.fee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbInPrepayMapperTests {
    @Autowired
    FinIpbInPrepayMapper inPrepayMapper;

    @Test
    void queryPrepays() {
        inPrepayMapper.queryPrepays(FinIpbInPrepayMapper.Key.builder().inpatientNo("ZY010000706272").build());
    }
}

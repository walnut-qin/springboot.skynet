package com.kaos.skynet.api.data.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprPrepayInMapperTests {
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    @Test
    void queryPrepayIn() {
        prepayInMapper.queryPrepayIn("2000003605", 10);
    }
}

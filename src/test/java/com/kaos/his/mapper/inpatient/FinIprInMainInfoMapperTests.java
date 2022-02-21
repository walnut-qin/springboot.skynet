package com.kaos.his.mapper.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprInMainInfoMapperTests {
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    @Test
    public void queryInMainInfo() {
        this.inMainInfoMapper.queryInMainInfo("ZY010000705856");
    }
}

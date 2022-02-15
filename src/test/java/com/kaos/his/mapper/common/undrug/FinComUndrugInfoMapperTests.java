package com.kaos.his.mapper.common.undrug;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComUndrugInfoMapperTests {
    @Autowired
    FinComUndrugInfoMapper undrugInfoMapper;

    @Test
    public void queryUndrugInfo() {
        this.undrugInfoMapper.queryUndrugInfo("F00000002872");
    }
}

package com.kaos.skynet.mapper.inpatient.escort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexChkMapperTests {
    @Autowired
    EscortAnnexChkMapper escortAnnexChkMapper;

    @Test
    public void queryAnnexChk() {
        this.escortAnnexChkMapper.queryAnnexChk("0000000008");
    }
}

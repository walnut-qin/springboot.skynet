package com.kaos.his.inpatient.escort;

import com.kaos.his.mapper.inpatient.escort.EscortAnnexChkMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexChkMapperTests {
    @Autowired
    EscortAnnexChkMapper escortAnnexChkMapper;

    @Test
    public void queryAnnexChk() {
        this.escortAnnexChkMapper.queryAnnexChk("annexNo");
    }
}

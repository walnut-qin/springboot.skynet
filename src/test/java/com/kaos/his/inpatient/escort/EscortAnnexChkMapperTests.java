package com.kaos.his.inpatient.escort;

import java.util.Date;

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
        this.escortAnnexChkMapper.queryAnnexChk("0000000008");
    }

    @Test
    public void queryAnnexChks() {
        this.escortAnnexChkMapper.queryAnnexChks("cardNo", null, null);
        this.escortAnnexChkMapper.queryAnnexChks("cardNo", new Date(), new Date());
    }
}

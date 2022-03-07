package com.kaos.skynet.api.mapper.inpatient.escort;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexInfoMapperTests {
    @Autowired
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    @Test
    public void queryAnnexInfo() {
        this.escortAnnexInfoMapper.queryAnnexInfo("annexNo");
    }

    @Test
    public void queryAnnexInfos() {
        this.escortAnnexInfoMapper.queryAnnexInfos("cardNo", new Date(), new Date(), null);
        this.escortAnnexInfoMapper.queryAnnexInfos("cardNo", new Date(), new Date(), true);
        this.escortAnnexInfoMapper.queryAnnexInfos("cardNo", new Date(), new Date(), false);
    }
}

package com.kaos.skynet.api.mapper.inpatient.escort;

import java.time.LocalDateTime;

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
        this.escortAnnexInfoMapper.queryAnnexInfos("cardNo", LocalDateTime.now(), LocalDateTime.now(), null);
        this.escortAnnexInfoMapper.queryAnnexInfos("cardNo", LocalDateTime.now(), LocalDateTime.now(), true);
        this.escortAnnexInfoMapper.queryAnnexInfos("cardNo", LocalDateTime.now(), LocalDateTime.now(), false);
    }
}

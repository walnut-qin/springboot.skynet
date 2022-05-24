package com.kaos.skynet.api.data.mapper.inpatient.escort.annex;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexInfoMapperTests {
    @Autowired
    EscortAnnexInfoMapper annexInfoMapper;

    @Test
    void queryAnnexInfo() {
        annexInfoMapper.queryAnnexInfo("0000000049");
    }

    @Test
    void queryAnnexInfos() {
        annexInfoMapper.queryAnnexInfos(EscortAnnexInfoMapper.Key.builder()
                .cardNo("2552076744")
                .checked(true).build());
    }
}

package com.kaos.skynet.api.data.mapper.inpatient.escort.annex;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortAnnexCheckMapperTests {
    @Autowired
    EscortAnnexCheckMapper annexCheckMapper;

    @Test
    void queryAnnexCheck() {
        annexCheckMapper.queryAnnexCheck("0000000049");
    }

    @Test
    void queryAnnexChecks() {
        annexCheckMapper.queryAnnexChecks(EscortAnnexCheckMapper.Key.builder()
                .cardNo("0123456789")
                .inspectBeginDate(LocalDateTime.now().plusDays(-2))
                .inspectEndDate(LocalDateTime.now()).build());
    }
}

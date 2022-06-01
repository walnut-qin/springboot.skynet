package com.kaos.skynet.api.data.mapper.inpatient.surgery;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsApplyMapperTests {
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    @Test
    void queryMetOpsApply() {
        metOpsApplyMapper.queryApply("test");
    }

    @Test
    void queryMetOpsApplies() {
        metOpsApplyMapper.queryApplies(MetOpsApplyMapper.Key.builder()
                .beginPreDate(LocalDateTime.now().minusMinutes(1))
                .build());
    }
}

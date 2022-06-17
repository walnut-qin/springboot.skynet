package com.kaos.skynet.api.data.his.mapper.pipe;

import java.time.LocalDateTime;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.api.data.his.mapper.pipe.lis.LisResultNewMapper.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LisResultNewMapperTests {
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    @Test
    void queryInspectResult() {
        this.lisResultNewMapper.queryInspectResults(Key.builder()
                .patientId("0001545200")
                .itemCodes(Lists.newArrayList("LYMPH#"))
                .beginDate(LocalDateTime.of(2021, 6, 28, 0, 0, 0))
                .endDate(LocalDateTime.of(2021, 6, 28, 23, 59, 59))
                .build());
    }
}

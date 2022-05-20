package com.kaos.skynet.api.data.mapper.pipe;

import java.time.LocalDateTime;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.mapper.pipe.lis.LisResultNewMapper;
import com.kaos.skynet.api.data.mapper.pipe.lis.LisResultNewMapper.Key;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LisResultNewMapperTests {
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    @Test
    public void queryInspectResult() {
        this.lisResultNewMapper.queryInspectResults(new Key() {
            {
                setPatientId("0001545200");
                setItemCodes(Lists.newArrayList("LYMPH#"));
                setBeginDate(LocalDateTime.of(2021, 6, 28, 0, 0, 0));
                setEndDate(LocalDateTime.of(2021, 6, 28, 23, 59, 59));
            }
        });
    }
}

package com.kaos.skynet.api.data.cache.pipe.lis;

import java.time.Duration;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.type.converter.duration.string.AgeDurationToStringConverter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j;

@Log4j
@SpringBootTest
public class LisResultNewCacheTests {
    @Autowired
    LisResultNewCache lisResultNewCache;

    @Test
    void get() {
        lisResultNewCache.get(LisResultNewCache.Key.builder()
            .patientId("0001545200")
            .itemCodes(Lists.newArrayList("LYMPH#"))
            .offset(14).build());
    }

    @Test
    void test(){
        Duration d = Duration.ofHours(15);
        log.info(new AgeDurationToStringConverter().convert(d));
    }
}

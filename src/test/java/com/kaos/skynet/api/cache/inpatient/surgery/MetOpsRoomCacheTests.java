package com.kaos.skynet.api.cache.inpatient.surgery;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsRoom;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsRoomCacheTests {
    @Autowired
    Cache<String, MetOpsRoom> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue("14");
    }
}

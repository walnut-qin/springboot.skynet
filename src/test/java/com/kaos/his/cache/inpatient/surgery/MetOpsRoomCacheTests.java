package com.kaos.his.cache.inpatient.surgery;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;

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

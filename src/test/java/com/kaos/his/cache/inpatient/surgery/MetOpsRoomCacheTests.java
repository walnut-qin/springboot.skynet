package com.kaos.his.cache.inpatient.surgery;

import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.inf.ICache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsRoomCacheTests {
    @Autowired
    ICache<String, MetOpsRoom> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue("14");
    }
}

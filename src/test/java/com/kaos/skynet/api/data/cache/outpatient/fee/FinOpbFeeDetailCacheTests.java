package com.kaos.skynet.api.data.cache.outpatient.fee;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOpbFeeDetailCacheTests {
    @Autowired
    FinOpbFeeDetailCache feeDetailCache;

    @Test
    void get() {
        feeDetailCache.get(FinOpbFeeDetailCache.Key.builder()
                .cardNo("2000003605")
                .itemCode("F00015")
                .offset(14).build());
    }
}

package com.kaos.skynet.api.data.cache.inpatient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprPrepayInCacheTests {
    @Autowired
    FinIprPrepayInCache prepayInCache;

    @Test
    void get() {
        prepayInCache.get(new FinIprPrepayInCache.Key() {
            {
                setCardNo("2000003605");
                setHappenNo(10);
            }
        });
    }
}

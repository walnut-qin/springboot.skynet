package com.kaos.helper.lock;

import com.kaos.helper.lock.impl.LockHelperImpl;
import com.kaos.his.HisApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HisApplication.class)
public class LockHelperTests {
    LockHelper lockHelper = new LockHelperImpl("测试锁", 10);

    @Test
    public void Test() {
        this.lockHelper.mapToLock("123testff49");
    }
}

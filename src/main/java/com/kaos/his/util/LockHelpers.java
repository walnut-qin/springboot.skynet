package com.kaos.his.util;

import com.kaos.his.util.helper.LockHelper;
import com.kaos.his.util.helper.impl.LockHelperImpl;

public final class LockHelpers {
    /**
     * 构造指定容量的匿名锁
     * 
     * @param size
     * @return
     */
    public static LockHelper newLockHelper(Integer size) {
        return new LockHelperImpl(size);
    }

    /**
     * 构造指定容量的有名锁
     * 
     * @param name
     * @param size
     * @return
     */
    public static LockHelper newLockHelper(String name, Integer size) {
        return new LockHelperImpl(name, size);
    }
}

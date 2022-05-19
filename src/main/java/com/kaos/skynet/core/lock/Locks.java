package com.kaos.skynet.core.lock;

import com.kaos.skynet.core.lock.impl.LockImpl;

import org.springframework.core.convert.converter.Converter;

public final class Locks {
    /**
     * 构造指定容量的匿名锁
     * 
     * @param size
     * @return
     */
    public static <K> Lock<K> newLockHelper(String name, Integer size, Converter<K, Integer> converter) {
        return new LockImpl<>(name, size, converter);
    }
}

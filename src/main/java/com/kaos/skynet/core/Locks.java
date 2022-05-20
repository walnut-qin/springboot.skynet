package com.kaos.skynet.core;

import com.kaos.skynet.core.type.thread.lock.Lock;
import com.kaos.skynet.core.type.thread.lock.impl.LockImpl;

import org.springframework.core.convert.converter.Converter;

public final class Locks {
    /**
     * 构造指定容量的匿名锁
     * 
     * @param size
     * @return
     */
    public static <K> Lock<K> newLock(String name, Integer size, Converter<K, Integer> converter) {
        return new LockImpl<>(name, size, converter);
    }

    /**
     * 构造指定容量的匿名锁
     * 
     * @param size
     * @return
     */
    public static <K> Lock<K> newLock(Integer size, Converter<K, Integer> converter) {
        return new LockImpl<>("匿名锁", size, converter);
    }
}

package com.kaos.skynet.core.util.thread.lock;

public interface LockFactory {
    /**
     * 获取锁对象
     * 
     * @param key 映射源
     * @return
     */
    <K> Lock getLock(K key);

    /**
     * 获取锁存量
     * 
     * @return
     */
    Integer size();

    /**
     * 获取锁名称
     * 
     * @return
     */
    String getLockName();
}

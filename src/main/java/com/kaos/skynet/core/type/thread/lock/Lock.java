package com.kaos.skynet.core.type.thread.lock;

public interface Lock {
    /**
     * 获取锁名
     * 
     * @return
     */
    String getName();

    /**
     * 获取原始锁对象
     * 
     * @return
     */
    Object getLock();
}

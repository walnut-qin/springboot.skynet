package com.kaos.helper.lock;

public interface LockHelper {
    /**
     * 映射到锁对象
     * 
     * @param src
     * @return
     */
    Object mapToLock(String src);
}

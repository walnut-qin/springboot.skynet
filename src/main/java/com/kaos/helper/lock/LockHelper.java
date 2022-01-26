package com.kaos.helper.lock;

public interface LockHelper {
    /**
     * 获取锁实体数量
     * 
     * @return
     */
    Integer getLockSize();

    /**
     * 映射到锁对象
     * 
     * @param src
     * @return
     */
    Object mapToLock(String src);
}

package com.kaos.skynet.util.helper;

public interface LockHelper {
    /**
     * 获取锁实体数量
     * 
     * @return
     */
    Integer getSize();

    /**
     * 映射到锁对象
     * 
     * @param src
     * @return
     */
    Object mapToLock(String src);
}

package com.kaos.skynet.core.type;

/**
 * 执行内容并返回T类型的值
 */
public interface Returnable<T> {
    /**
     * 执行代码
     */
    T run();
}

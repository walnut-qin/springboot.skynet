package com.kaos.skynet.core.type;

/**
 * 隧道，封装一些简单固定的逻辑性质的数据转换
 */
public interface Tunnel<S, T> {
    /**
     * 遂穿
     * 
     * @param source
     * @return
     */
    T tunneling(S source);
}

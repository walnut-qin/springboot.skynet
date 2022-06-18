package com.kaos.skynet.core.type;

/**
 * 带路党 (^_−)☆
 */
public interface Router<S, R> {
    /**
     * 转换器
     * 
     * @param source
     * @return
     */
    R route(S source);
}

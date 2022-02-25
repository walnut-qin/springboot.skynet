package com.kaos.his.util.helper;

import java.util.List;

public interface ListHelper {
    /**
     * 获取第一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    <T> T getFirst(List<T> list);

    /**
     * 获取最后一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    <T> T getLast(List<T> list);

    /**
     * 拼接字符串列表
     * 
     * @param seperator
     * @param list
     * @return
     */
    String join(String separator, List<String> list);
}

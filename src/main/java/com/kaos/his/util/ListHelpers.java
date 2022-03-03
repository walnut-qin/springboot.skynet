package com.kaos.his.util;

import java.util.List;

public final class ListHelpers {
    /**
     * 获取第一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    public static <T> T getFirst(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 获取最后一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    public static <T> T getLast(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        } else {
            return list.get(list.size() - 1);
        }
    }
}

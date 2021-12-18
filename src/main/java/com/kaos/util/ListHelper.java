package com.kaos.util;

import java.util.List;

/**
 * List类助手
 */
public class ListHelper {
    /**
     * 获取容器中的最后一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    public static <T> T GetLast(List<T> list) {
        // 若列表为空，直接返回null
        if (list == null || list.isEmpty()) {
            return null;
        }

        return list.get(list.size() - 1);
    }
}

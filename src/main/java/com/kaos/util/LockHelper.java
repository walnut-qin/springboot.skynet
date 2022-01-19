package com.kaos.util;

import java.util.List;

public class LockHelper {
    /**
     * 将字符串映射到锁索引
     * 
     * @param input
     * @return
     */
    public static Object mapToLock(String input, List<Object> locks) {
        // 计算索引
        input = input.replaceAll("[^0-9]", "");
        var idx = Long.valueOf(input) % locks.size();

        return locks.get((int) idx);
    }
}

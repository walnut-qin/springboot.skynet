package com.kaos.skynet.core;

/**
 * Integer对象的一些工具
 */
public final class IntegerUtils {
    /**
     * 比较两个Integer对象是否相等
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static boolean equals(Integer int1, Integer int2) {
        if (int1 == int2) {
            return true;
        }
        if (int1 == null || int2 == null) {
            return false;
        }
        return int1.equals(int2);
    }
}

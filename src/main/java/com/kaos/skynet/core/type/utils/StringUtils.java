package com.kaos.skynet.core.type.utils;

public final class StringUtils {
    /**
     * 空字符串
     */
    final static String EMPTY = "";

    /**
     * 比较两个Integer对象是否相等
     * 
     * @param s1
     * @param s2
     * @return
     */
    public static boolean equals(String s1, String s2) {
        if (s1 == s2) {
            return true;
        }
        if (s1 == null || s2 == null) {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * 比较两个Integer的大小
     * 
     * @param int1
     * @param int2
     * @param nullIsLess
     * @return
     */
    public static int compare(String s1, String s2, boolean nullIsLess) {
        if (s1 == s2) {
            return 0;
        }
        if (s1 == null) {
            return nullIsLess ? -1 : 1;
        }
        if (s2 == null) {
            return nullIsLess ? 1 : -1;
        }
        return s1.compareTo(s2);
    }

    /**
     * 比较两个数大小
     * 
     * @param int1
     * @param int2
     * @return
     */
    public static int compare(String s1, String s2) {
        return compare(s1, s2, true);
    }

    /**
     * 左拼接字符
     * 
     * @param str     原始字符串
     * @param size    拼接后的长度
     * @param padChar 用于拼接的字符
     * @return
     */
    public static String leftPad(String str, int size, char padChar) {
        return org.apache.commons.lang3.StringUtils.leftPad(str, size, padChar);
    }
}

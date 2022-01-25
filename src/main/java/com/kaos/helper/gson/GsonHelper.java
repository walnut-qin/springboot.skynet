package com.kaos.helper.gson;

public interface GsonHelper {
    /**
     * json序列化
     * 
     * @param src
     * @return
     */
    String toJson(Object src);

    /**
     * json反序列化
     * 
     * @param <T>     泛型T
     * @param json    原始字符串
     * @param typeOfT 反序列类型
     * @return
     */
    <T> T FromJson(String json, Class<T> typeOfT);
}

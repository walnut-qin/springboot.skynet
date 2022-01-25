package com.kaos.helper.gson;

import com.google.gson.Gson;

public interface GsonHelper {
    /**
     * 获取Gson对象
     * 
     * @return
     */
    Gson getGson();

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

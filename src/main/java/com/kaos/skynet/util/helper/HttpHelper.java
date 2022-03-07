package com.kaos.skynet.util.helper;

public interface HttpHelper {
    /**
     * 发送POST请求
     * 
     * @param <T>
     * @param url
     * @param reqBody
     * @param classOfT
     * @return
     */
    <T> T postForObject(String url, Object reqBody, Class<T> classOfT);
}

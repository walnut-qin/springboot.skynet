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

    /**
     * 发送GET请求
     * 
     * @param <T>
     * @param url
     * @param classOfT
     * @param uriVariables
     * @return
     */
    <T> T getForObject(String url, Class<T> classOfT, Object... uriVariables);
}

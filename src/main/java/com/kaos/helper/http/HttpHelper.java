package com.kaos.helper.http;

public interface HttpHelper {
    /**
     * 发送post请求
     * 
     * @param <T>      响应类型T
     * @param url      请求url
     * @param request  请求body
     * @param classOfT 响应类型
     * @return
     */
    <T> T postForObject(String url, Object request, Class<T> classOfT);
}

package com.kaos.skynet.core.http.handler;

import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractHttpHandler {
    /**
     * HTTP句柄
     */
    RestTemplate restTemplate;

    /**
     * 对端IP
     */
    String ip;

    /**
     * 端口号
     */
    Integer port;

    /**
     * 发送post请求
     * 
     * @param <T>
     * @param url
     * @param reqBody
     * @param classOfT
     * @return
     */
    public <T> T postForObject(String url, Object reqBody, Class<T> classOfT) {
        // 格式化url
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = String.format("http://%s:%d/%s", ip, port, url);
        return this.restTemplate.postForObject(url, reqBody, classOfT);
    }

    /**
     * 发送GET请求
     * 
     * @param <T>
     * @param url
     * @param classOfT
     * @param uriVariables
     * @return
     */
    public <T> T getForObject(String url, Class<T> classOfT, Object... uriVariables) {
        // 格式化url
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = String.format("http://%s:%d/%s", ip, port, url);
        return this.restTemplate.getForObject(url, classOfT, uriVariables);
    }
}

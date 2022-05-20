package com.kaos.skynet.core.type.http.client.impl;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.Gsons;
import com.kaos.skynet.core.type.http.client.HttpClient;
import com.kaos.skynet.core.type.http.message.converter.BooleanHttpMessageConverter;
import com.kaos.skynet.core.type.http.message.converter.DoubleHttpMessageConverter;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpClientImpl implements HttpClient {
    /**
     * HTTP接口
     */
    final RestTemplate restTemplate = new RestTemplate(
            Lists.newArrayList(
                    new GsonHttpMessageConverter(Gsons.newGson()),
                    new BooleanHttpMessageConverter(),
                    new DoubleHttpMessageConverter()));

    /**
     * 对端IP地址
     */
    String ip = null;

    /**
     * 对端端口号
     */
    Integer port = null;

    /**
     * 构造函数
     * 
     * @param socketInfo 服务器地址
     */
    public HttpClientImpl(String ip, Integer port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public <T> T postForObject(String url, Object reqBody, Class<T> classOfT) {
        // 格式化url
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = String.format("http://%s:%d/%s", ip, port, url);
        return this.restTemplate.postForObject(url, reqBody, classOfT);
    }

    @Override
    public <T> T getForObject(String url, Class<T> classOfT, Object... uriVariables) {
        // 格式化url
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = String.format("http://%s:%d/%s", ip, port, url);
        return this.restTemplate.getForObject(url, classOfT, uriVariables);
    }
}

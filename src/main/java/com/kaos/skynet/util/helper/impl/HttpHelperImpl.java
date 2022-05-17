package com.kaos.skynet.util.helper.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kaos.skynet.util.Gsons;
import com.kaos.skynet.util.helper.HttpHelper;

import org.javatuples.Pair;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpHelperImpl implements HttpHelper {
    /**
     * HTTP接口
     */
    RestTemplate restTemplate = null;

    /**
     * 套接字
     */
    Pair<String, Integer> socketInfo = null;

    /**
     * 构造函数
     * 
     * @param socketInfo 服务器地址
     */
    public HttpHelperImpl(Pair<String, Integer> socketInfo) {
        this.socketInfo = socketInfo;
        this.restTemplate = new RestTemplate(Lists.newArrayList(new GsonHttpMessageConverter(Gsons.newGson())));
    }

    /**
     * 指定gson工具的构造
     * 
     * @param socketInfo 服务器地址
     * @param gson       序列化工具
     */
    public HttpHelperImpl(Pair<String, Integer> socketInfo, Gson gson) {
        this.socketInfo = socketInfo;
        this.restTemplate = new RestTemplate(Lists.newArrayList(new GsonHttpMessageConverter(gson)));
    }

    @Override
    public <T> T postForObject(String url, Object reqBody, Class<T> classOfT) {
        // 格式化url
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = String.format("http://%s:%d/%s", this.socketInfo.getValue0(), this.socketInfo.getValue1(), url);
        return this.restTemplate.postForObject(url, reqBody, classOfT);
    }

    @Override
    public <T> T getForObject(String url, Class<T> classOfT, Object... uriVariables) {
        // 格式化url
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = String.format("http://%s:%d/%s", this.socketInfo.getValue0(), this.socketInfo.getValue1(), url);
        return this.restTemplate.getForObject(url, classOfT, uriVariables);
    }
}

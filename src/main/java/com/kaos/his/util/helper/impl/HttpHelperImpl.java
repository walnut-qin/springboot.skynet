package com.kaos.his.util.helper.impl;

import com.google.common.collect.Lists;
import com.kaos.his.util.Gsons;
import com.kaos.his.util.helper.HttpHelper;

import org.javatuples.Pair;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpHelperImpl implements HttpHelper {
    /**
     * HTTP接口
     */
    RestTemplate restTemplate = new RestTemplate(Lists.newArrayList(new GsonHttpMessageConverter(Gsons.newGson())));

    /**
     * 套接字
     */
    Pair<String, Integer> socketInfo = null;

    /**
     * 构造函数
     */
    public HttpHelperImpl(Pair<String, Integer> socketInfo) {
        this.socketInfo = socketInfo;
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
}

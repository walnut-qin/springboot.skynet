package com.kaos.helper.http.impl;

import java.util.ArrayList;

import com.kaos.helper.gson.impl.GsonHelperImpl;
import com.kaos.helper.http.HttpHelper;
import com.kaos.helper.http.enums.ServerEnum;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class HttpHelperImpl implements HttpHelper {
    /**
     * web接口
     */
    RestTemplate restTemplate = new RestTemplate(new ArrayList<HttpMessageConverter<?>>() {
        {
            add(new GsonHttpMessageConverter(new GsonHelperImpl("yyyy-MM-dd").getGson()));
        }
    });

    /**
     * 服务器对端
     */
    ServerEnum server = null;

    /**
     * 私有化构造函数
     * 
     * @param server
     */
    HttpHelperImpl(ServerEnum server) {
        this.server = server;
    }

    /**
     * 读取器
     * 
     * @param server
     * @return
     */
    public static HttpHelperImpl getHelper(ServerEnum server) {
        return new HttpHelperImpl(server);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> classOfT) {
        // 删除"/"前缀
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        // 拼接IP/PORT
        url = String.format("http://%s:%s/%s", this.server.getValue(), this.server.getDescription(), url);
        // 发送请求
        return this.restTemplate.postForObject(url, request, classOfT);
    }
}
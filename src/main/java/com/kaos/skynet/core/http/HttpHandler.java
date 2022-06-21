package com.kaos.skynet.core.http;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.kaos.skynet.core.json.GsonWrapper;

import org.springframework.http.converter.json.AbstractJsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

@Log4j
public class HttpHandler {
    /**
     * 对端IP
     */
    String host;

    /**
     * 端口号
     */
    Integer port;

    /**
     * HTTP句柄
     */
    final RestTemplate restTemplate;

    /**
     * 计时器
     */
    final Stopwatch timer;

    /**
     * 构造函数
     */
    public HttpHandler(String host, Integer port) {
        // 初始化对端
        this.host = host.replaceAll("http://", "");
        this.port = port;

        // 初始化RestTemplate
        restTemplate = new RestTemplate(Lists.newArrayList(new JsonHttpMessageConverter()));

        // 初始化计时器
        timer = Stopwatch.createUnstarted();
    }

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
        // 启动计时器
        timer.reset();
        timer.start();

        try {
            // 格式化url
            if (url.startsWith("/")) {
                url = url.replaceFirst("/", "");
            }
            url = String.format("http://%s:%d/%s", host, port, url);
            return this.restTemplate.postForObject(url, reqBody, classOfT);
        } finally {
            // 停止计时
            timer.stop();

            // 记录日志
            log.info(String.format("网络调用<%s>耗时<%s>", url, timer.toString()));
        }
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
        url = String.format("http://%s:%d/%s", host, port, url);
        return this.restTemplate.getForObject(url, classOfT, uriVariables);
    }

    /**
     * json适配器
     */
    static class JsonHttpMessageConverter extends AbstractJsonHttpMessageConverter {
        /**
         * 序列化工具
         */
        final static GsonWrapper gsonWrapper = new GsonWrapper();

        @Override
        protected Object readInternal(Type resolvedType, Reader reader) throws Exception {
            return gsonWrapper.fromJson(reader, resolvedType);
        }

        @Override
        protected void writeInternal(Object object, Type type, Writer writer) throws Exception {
            gsonWrapper.toJson(object, writer);
        }
    }
}

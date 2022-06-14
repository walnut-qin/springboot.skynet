package com.kaos.skynet.core.http.handler;

import com.google.common.base.Stopwatch;

import org.springframework.web.client.RestTemplate;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
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
     * 计时器
     */
    final Stopwatch timer = Stopwatch.createUnstarted();

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
            url = String.format("http://%s:%d/%s", ip, port, url);
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
        url = String.format("http://%s:%d/%s", ip, port, url);
        return this.restTemplate.getForObject(url, classOfT, uriVariables);
    }
}

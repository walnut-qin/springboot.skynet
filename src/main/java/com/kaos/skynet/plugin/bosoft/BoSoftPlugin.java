package com.kaos.skynet.plugin.bosoft;

import com.google.common.collect.Lists;
import com.kaos.skynet.core.http.converter.JsonHttpMessageConverter;
import com.kaos.skynet.core.http.handler.AbstractHttpHandler;
import com.kaos.skynet.plugin.bosoft.wrapper.ReqWrapper;
import com.kaos.skynet.plugin.bosoft.wrapper.RspWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BoSoftPlugin {
    /**
     * Http句柄
     */
    AbstractHttpHandler httpHandler;

    /**
     * 请求包装器
     */
    @Autowired
    ReqWrapper reqWrapper;

    /**
     * 响应包装器
     */
    @Autowired
    RspWrapper rspWrapper;

    /**
     * 服务器IP
     */
    final static String ip = "172.16.100.123";

    /**
     * 服务器PORT
     */
    final static Integer port = 17001;

    /**
     * API前缀
     */
    final static String apiPrefix = "/medical-web/api/medical/";

    /**
     * 构造函数
     * 
     * @param jsonHttpMessageConverter
     */
    BoSoftPlugin(JsonHttpMessageConverter jsonHttpMessageConverter) {
        // 构造HTTP处理器
        httpHandler = new AbstractHttpHandler(
                new RestTemplate(Lists.newArrayList(jsonHttpMessageConverter)),
                ip, port) {
        };
    }

    /**
     * 发送POST请求
     * 
     * @param <R>      请求类型
     * @param <S>      响应类型
     * @param apiType  业务类型
     * @param data     请求数据
     * @param classOfS 响应数据类型
     * @return
     */
    public <R, S> S postForObject(String apiType, R data, Class<S> classOfS) {
        // 加密请求信息
        var reqBody = reqWrapper.wrapData(data);

        // 发送post请求
        var rspBody = httpHandler.postForObject(apiPrefix.concat(apiType), reqBody, RspWrapper.RspBody.class);

        // 解密并抽取响应
        return rspWrapper.disassemble(rspBody, classOfS);
    }
}

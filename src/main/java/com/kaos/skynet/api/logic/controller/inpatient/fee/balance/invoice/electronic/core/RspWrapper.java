package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic.core;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.kaos.skynet.core.json.Json;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class RspWrapper {
    /**
     * 响应数据
     */
    private String data;

    /**
     * 干扰数据
     */
    private String noise;

    /**
     * 签名
     */
    private String sign;

    /**
     * 拆卸包装
     * 
     * @param json
     * @return
     */
    public <T> T disassemble(Json json, Class<T> classOfT) {
        try {
            // base64解码
            String resultWrapperStr = new String(Base64.getDecoder().decode(data), "UTF-8");

            // 反序列化出resultWrapper
            ResultWrapper resultWrapper = json.fromJson(resultWrapperStr, ResultWrapper.class);

            // 反序列化出message
            String messageStr = new String(Base64.getDecoder().decode(resultWrapper.getMessage()), "UTF-8");

            // 判断响应
            if (!resultWrapper.getResult().equals("S0000")) {
                log.error(messageStr);
                throw new RuntimeException(messageStr);
            } else {
                return json.fromJson(messageStr, classOfT);
            }
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Getter
    public static class ResultWrapper {
        /**
         * 响应结果
         */
        private String result;

        /**
         * 响应消息
         */
        private String message;
    }
}

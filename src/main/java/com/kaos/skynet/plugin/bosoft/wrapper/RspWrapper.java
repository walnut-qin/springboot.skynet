package com.kaos.skynet.plugin.bosoft.wrapper;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class RspWrapper {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 拆卸包装
     * 
     * @param json
     * @return
     */
    public <T> T disassemble(ResultModel result, Class<T> classOfT) {
        try {
            // base64解码
            String resultWrapperStr = new String(Base64.getDecoder().decode(result.data), "UTF-8");

            // 反序列化出resultWrapper
            DataModel data = json.fromJson(resultWrapperStr, DataModel.class);

            // 反序列化出message
            String messageStr = new String(Base64.getDecoder().decode(data.getMessage()), "UTF-8");

            // 判断响应
            if (!data.getResult().equals("S0000")) {
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
    public static class ResultModel {
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
    }

    @Getter
    public static class DataModel {
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

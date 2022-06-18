package com.kaos.skynet.plugin.bosoft.wrapper;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;

import com.kaos.skynet.core.json.Json;
import com.kaos.skynet.core.type.converter.LocalDateTimeToStringConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Component
public class ReqWrapper {
    /**
     * 时间转换器
     */
    final LocalDateTimeToStringConverter noiseConverter = new LocalDateTimeToStringConverter("yyyyMMddHHmmssSSS");

    /**
     * 加密Key值
     */
    final String key = "dd1893efc3e234a2d2826ad107";

    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 包装数据
     * 
     * @param data
     * @return
     */
    public <T> ReqBody wrapData(T data) {
        try {
            // 构造请求body
            ReqBody reqBody = new ReqBody();

            // 计算数据
            reqBody.data = Base64.getEncoder().encodeToString(json.toJson(data).getBytes("UTF-8"));

            // 计算noise
            reqBody.noise = noiseConverter.convert(LocalDateTime.now());

            // 计算签名
            String orgStr = String.format("appid=%s&data=%s&noise=%s&key=%s&version=%s",
                    reqBody.appid,
                    reqBody.data,
                    reqBody.noise,
                    key,
                    reqBody.version);
            reqBody.sign = DigestUtils.md5DigestAsHex(orgStr.getBytes()).toUpperCase();

            return reqBody;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Getter
    private static class ReqBody {
        /**
         * 请求ID
         */
        String appid = "XYSZXYY1551463";

        /**
         * 业务数据
         */
        String data;

        /**
         * 用时间戳生成noise
         */
        String noise;

        /**
         * 版本号
         */
        String version = "1.0";

        /**
         * 签名
         */
        String sign;
    }
}

package com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic.core;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;

import com.kaos.skynet.api.logic.controller.inpatient.fee.balance.invoice.electronic.core.converter.LocalDateTimeToStringConverter;
import com.kaos.skynet.core.json.Json;

import org.springframework.util.DigestUtils;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class ReqWrapper {
    /**
     * 请求ID
     */
    private final String appid = "XYSZXYY1551463";

    /**
     * 业务数据
     */
    private String data;

    /**
     * 用时间戳生成noise
     */
    private String noise;

    /**
     * 版本号
     */
    private final String version = "1.0";

    /**
     * 签名
     */
    private String sign;

    /**
     * 时间转换器
     */
    private static final LocalDateTimeToStringConverter localDateTimeToStringConverter = new LocalDateTimeToStringConverter();

    /**
     * 加密Key值
     */
    private static final String key = "dd1893efc3e234a2d2826ad107";

    /**
     * 包装数据
     * 
     * @param data
     * @return
     */
    public static <T> ReqWrapper wrapData(Json json, T data) {
        try {
            ReqWrapper reqWrapper = new ReqWrapper();

            // 序列化数据
            String dataStr = json.toJson(data);

            // 计算数据
            reqWrapper.data = Base64.getEncoder().encodeToString(dataStr.getBytes("UTF-8"));

            // 计算noise
            reqWrapper.noise = localDateTimeToStringConverter.convert(LocalDateTime.now());

            // 计算签名
            String orgStr = String.format("appid=%s&data=%s&noise=%s&key=%s&version=%s",
                    reqWrapper.appid,
                    reqWrapper.data,
                    reqWrapper.noise,
                    key,
                    reqWrapper.version);
            reqWrapper.sign = DigestUtils.md5DigestAsHex(orgStr.getBytes()).toUpperCase();

            return reqWrapper;
        } catch (UnsupportedEncodingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}

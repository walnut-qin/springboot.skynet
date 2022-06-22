package com.kaos.skynet.plugin.bosoft;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;

import com.kaos.skynet.core.config.spring.net.RestTemplateWrapper;
import com.kaos.skynet.core.util.converter.LocalDateTimeToStringConverter;
import com.kaos.skynet.core.util.json.GsonWrapper;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import lombok.extern.log4j.Log4j;

@Component
public class BoSoftPlugin {
    /**
     * Http句柄
     */
    RestTemplateWrapper restTemplateWrapper = new RestTemplateWrapper("172.16.100.123", 17001);

    /**
     * 请求包装器
     */
    Wrapper.ReqWrapper reqWrapper = new Wrapper.ReqWrapper();

    /**
     * 响应包装器
     */
    Wrapper.RspWrapper rspWrapper = new Wrapper.RspWrapper();

    /**
     * API前缀
     */
    String apiPrefix = "/medical-web/api/medical/";

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
        var rspBody = restTemplateWrapper.post(apiPrefix.concat(apiType), reqBody, Wrapper.RspWrapper.RspBody.class);

        // 解密并抽取响应
        return rspWrapper.disassemble(rspBody, classOfS);
    }

    static class Wrapper {
        /**
         * 请求包装器
         */
        @Log4j
        static class ReqWrapper {
            /**
             * 时间转换器
             */
            LocalDateTimeToStringConverter noiseConverter = new LocalDateTimeToStringConverter("yyyyMMddHHmmssSSS");

            /**
             * 加密Key值
             */
            String key = "dd1893efc3e234a2d2826ad107";

            /**
             * json工具
             */
            GsonWrapper gsonWrapper = new GsonWrapper();

            /**
             * 包装数据
             * 
             * @param data
             * @return
             */
            <T> ReqBody wrapData(T data) {
                try {
                    // 构造请求body
                    ReqBody reqBody = new ReqBody();

                    // 计算数据
                    reqBody.data = Base64.getEncoder().encodeToString(gsonWrapper.toJson(data).getBytes("UTF-8"));

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

            static class ReqBody {
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

        /**
         * 响应包装器
         */
        @Log4j
        static class RspWrapper {
            /**
             * 序列化工具
             */
            GsonWrapper gsonWrapper = new GsonWrapper();

            /**
             * 拆卸包装
             * 
             * @param json
             * @return
             */
            public <T> T disassemble(RspBody result, Class<T> classOfT) {
                try {
                    // base64解码
                    String resultWrapperStr = new String(Base64.getDecoder().decode(result.data), "UTF-8");

                    // 反序列化出resultWrapper
                    RspBody.Data data = gsonWrapper.fromJson(resultWrapperStr, RspBody.Data.class);

                    // 反序列化出message
                    String messageStr = new String(Base64.getDecoder().decode(data.message), "UTF-8");

                    // 判断响应
                    if (!data.result.equals("S0000")) {
                        log.error(messageStr);
                        throw new RuntimeException(messageStr);
                    } else {
                        return gsonWrapper.fromJson(messageStr, classOfT);
                    }
                } catch (UnsupportedEncodingException e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e.getMessage());
                }
            }

            static class RspBody {
                /**
                 * 响应数据
                 */
                String data;

                /**
                 * 干扰数据
                 */
                String noise;

                /**
                 * 签名
                 */
                String sign;

                /**
                 * Data域
                 */
                static class Data {
                    /**
                     * 响应结果
                     */
                    String result;

                    /**
                     * 响应消息
                     */
                    String message;
                }
            }
        }
    }
}

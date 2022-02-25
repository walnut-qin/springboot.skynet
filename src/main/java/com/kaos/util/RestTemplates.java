package com.kaos.util;

import java.util.ArrayList;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public final class RestTemplates {
    /**
     * HIS服务器地址
     */
    public final static String HIS_SERVER = "172.16.100.252:8025";

    /**
     * DOCARE服务器地址
     */
    public final static String DOCARE_SERVER = "172.16.100.252:8002";

    /**
     * PROXY服务器地址
     */
    public final static String PROXY_SERVER = "172.16.100.252:8003";

    /**
     * 创建一个网络接口实体
     */
    public static RestTemplate newRestTemplate() {
        return new RestTemplate(new ArrayList<>() {
            {
                add(new GsonHttpMessageConverter(Gsons.newGson()));
            }
        });
    }
}

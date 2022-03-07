package com.kaos.skynet.util;

import com.kaos.skynet.util.helper.HttpHelper;
import com.kaos.skynet.util.helper.impl.HttpHelperImpl;

import org.javatuples.Pair;

public final class HttpHelpers {
    /**
     * his服务器
     */
    public final static Pair<String, Integer> HIS_SERVER = new Pair<>("172.16.100.252", 8025);

    /**
     * his服务器
     */
    public final static Pair<String, Integer> DOCARE_SERVER = new Pair<>("172.16.100.252", 8002);

    /**
     * his服务器
     */
    public final static Pair<String, Integer> PROXY_SERVER = new Pair<>("172.16.100.252", 8003);

    /**
     * 创建一个网络接口实体
     */
    public static HttpHelper newHttpClient(Pair<String, Integer> server) {
        return new HttpHelperImpl(server);
    }
}

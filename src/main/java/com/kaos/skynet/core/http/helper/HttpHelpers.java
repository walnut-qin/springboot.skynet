package com.kaos.skynet.core.http.helper;

import com.kaos.skynet.core.http.helper.impl.HttpHelperImpl;

import lombok.AllArgsConstructor;

public final class HttpHelpers {
    /**
     * 创建一个网络接口实体
     */
    public static HttpHelper newHttpClient(Node node) {
        return new HttpHelperImpl(node.ip, node.port);
    }

    /**
     * 网络节点结构
     */
    @AllArgsConstructor
    static class Node {
        /**
         * IP
         */
        String ip;

        /**
         * 端口号
         */
        Integer port;
    }

    public final static Node hisNode = new Node("172.16.100.252", 8025);

    public final static Node docareNode = new Node("172.16.100.253", 8025);

    public final static Node proxyNode = new Node("172.16.100.50", 8025);
}

package com.kaos.skynet.core.http;

import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.core.json.gson.adapter.enums.ValueEnumTypeAdapter;
import com.kaos.skynet.core.type.Enum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RspWrapper<T> {
    /**
     * 错误码
     */
    @JsonAdapter(ValueEnumTypeAdapter.class)
    private CodeEnum code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应信息
     */
    private T data;

    @Getter
    @AllArgsConstructor
    public enum CodeEnum implements Enum {
        E0000("E0000", "成功"),
        E0001("E9999", "其他异常");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}

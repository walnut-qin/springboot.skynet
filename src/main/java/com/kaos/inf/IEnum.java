package com.kaos.inf;

/**
 * 定制枚举接口
 */
public interface IEnum {
    /**
     * 值用于mybatis映射数据库
     * 
     * @return
     */
    String getValue();

    /**
     * 值用于gson序列化与反序列化
     * 
     * @return
     */
    String getDescription();
}

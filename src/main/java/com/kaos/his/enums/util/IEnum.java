package com.kaos.his.enums.util;

public interface IEnum<E> {
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

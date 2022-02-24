package com.kaos.inf;

import java.lang.constant.Constable;

/**
 * 定制枚举接口
 */
public interface IEnum extends Constable {
    /**
     * 写入数据库的值
     * 
     * @return
     */
    String getValue();

    /**
     * 序列化入json的值
     * 
     * @return
     */
    String getDescription();
}

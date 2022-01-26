package com.kaos.helper.type;

import java.util.Date;
import java.util.List;

import com.kaos.helper.type.entity.Age;

public interface TypeHelper {
    /**
     * 获取年龄实体
     * 
     * @param birthday
     * @return
     */
    Age getAge(Date birthday);

    /**
     * 获取列表最后一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    <T> T getFirst(List<T> list);

    /**
     * 获取列表最后一个元素
     * 
     * @param <T>
     * @param list
     * @return
     */
    <T> T getLast(List<T> list);
}

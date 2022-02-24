package com.kaos.helper.type;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.kaos.helper.type.entity.Age;
import com.kaos.his.enums.common.NoonEnum;

public interface TypeHelper {
    /**
     * 组合字符串
     * 
     * @param separator
     * @param items
     * @return
     */
    String join(String separator, Collection<String> items);

    /**
     * 获取年龄实体
     * 
     * @param birthday
     * @return
     */
    Age getAge(Date birthday);

    /**
     * 获取午别信息
     * 
     * @param date 目标日期
     * @return
     */
    NoonEnum getNoon(Date date);

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

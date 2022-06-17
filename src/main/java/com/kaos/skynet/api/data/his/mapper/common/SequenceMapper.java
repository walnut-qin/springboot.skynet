package com.kaos.skynet.api.data.his.mapper.common;

import org.apache.ibatis.annotations.Param;

public interface SequenceMapper {
    /**
     * 查询序列的值
     * 
     * @param name
     * @return
     */
    String query(@Param("name") String name);
}

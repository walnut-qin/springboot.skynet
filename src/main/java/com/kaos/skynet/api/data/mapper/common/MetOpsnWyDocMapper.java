package com.kaos.skynet.api.data.mapper.common;

import com.kaos.skynet.api.data.entity.common.MetOpsnWyDoc;

public interface MetOpsnWyDocMapper {
    /**
     * 主键查询
     * 
     * @param emplCode 员工编码；值为 {@code null} 时，将 IS NULL 作为判断条件
     * @return
     */
    MetOpsnWyDoc queryEmpl(String emplCode);
}

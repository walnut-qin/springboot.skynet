package com.kaos.skynet.api.mapper.pipe.pacs;

import com.kaos.skynet.entity.pipe.pacs.PacsCropImageRec;

public interface PacsCropImageRecMapper {
    /**
     * 根据关联号查询记录
     * 
     * @param refer 关联号（唯一）
     * @return
     */
    PacsCropImageRec queryRec(String refer);

    /**
     * 插入一条新数据
     * 
     * @param rec
     * @return
     */
    Integer insertRec(PacsCropImageRec rec);
}

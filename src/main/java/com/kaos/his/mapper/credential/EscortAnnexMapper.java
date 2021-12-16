package com.kaos.his.mapper.credential;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.credential.EscortAnnex;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EscortAnnexMapper {
    /**
     * 查询陪护人附件，按照附件编码排序
     * 
     * @param cardNo 卡号
     * @param offset 时间差，单位：天
     * @return
     */
    List<EscortAnnex> QueryAnnex(String cardNo, Integer offset);

    /**
     * 插入新纪录
     * 
     * @param escortAnnex
     */
    void InsertAnnex(@Param("cardNo") String cardNo, @Param("picUrl") String picUrl);

    /**
     * 审核
     * 
     * @param cardNo
     * @param operDate
     */
    void CfmAnnex(@Param("annexNo") String annexNo, @Param("result") Boolean result, @Param("natDate") Date natDate);
}

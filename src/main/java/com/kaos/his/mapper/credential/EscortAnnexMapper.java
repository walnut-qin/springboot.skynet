package com.kaos.his.mapper.credential;

import java.util.Date;

import com.kaos.his.entity.credential.EscortAnnex;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EscortAnnexMapper {
    /**
     * 查询陪护人附件
     * 
     * @param cardNo
     * @return
     */
    EscortAnnex QueryEscortAnnex(String cardNo);

    /**
     * 插入新纪录
     * 
     * @param escortAnnex
     */
    void InsertEscortAnnex(EscortAnnex escortAnnex);

    /**
     * 更新记录
     * 
     * @param cardNo
     * @param operDate
     * @param url
     */
    void UpdateEscortAnnex(@Param("cardNo") String cardNo, @Param("operDate") Date operDate, @Param("url") String url);
}

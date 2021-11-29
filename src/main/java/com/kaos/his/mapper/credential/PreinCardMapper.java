package com.kaos.his.mapper.credential;

import com.kaos.his.entity.credential.PreinCard;

import org.springframework.stereotype.Repository;

@Repository
public interface PreinCardMapper {
    /**
     * 获取住院证
     * 
     * @param cardNo
     * @param happenNo
     * @return
     */
    PreinCard QueryPreinCard(String cardNo, Integer happenNo);

    /**
     * 获取指定患者的最近的一张住院证
     * 
     * @param cardNo
     * @return
     */
    PreinCard QueryLatestPreinCard(String cardNo);

    /**
     * 插入一条陪护证VIP卡号
     * 
     * @param helperCardNo
     */
    void InsertEscortVip(PreinCard preinCard);
}

package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.EscortCard;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortCardMapper {
    /**
     * 获取陪护证，主键查询
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    EscortCard QueryEscortCard(String escortNo);

    /**
     * 查询陪护人关联的未注销的陪护证列表
     * 
     * @param helperCardNo 陪护人卡号
     * @return 陪护证列表
     */
    List<EscortCard> QueryHelperRegisteredEscortCards(String helperCardNo);

    /**
     * 查询患者住院证关联的未注销陪护证列表
     * 
     * @param patientCardNo 患者卡号
     * @param happenNo      住院证编号
     * @return
     */
    List<EscortCard> QueryPatientRegisteredEscortCards(String patientCardNo, Integer happenNo);

    /**
     * 查询指定陪护关系中最后一张陪护证
     * 
     * @param patientCardNo 患者卡号
     * @param happenNo      住院证编号
     * @param helperCardNo  陪护人卡号
     * @return 陪护证列表
     */
    EscortCard QueryLastEscortCards(String patientCardNo, Integer happenNo, String helperCardNo);

    /**
     * 查询所有当前活跃的陪护证
     * 
     * @return
     */
    List<EscortCard> QueryAllActivedEscortCards();

    /**
     * 添加一个新的陪护证
     * 
     * @param escortCard
     */
    void InsertEscortCard(EscortCard escortCard);
}

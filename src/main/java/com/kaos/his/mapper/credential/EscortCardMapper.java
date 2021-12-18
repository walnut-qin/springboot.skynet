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
    EscortCard QueryEscort(String escortNo);

    /**
     * 查询陪护人关联的陪护证列表
     * 
     * @param helperCardNo 陪护人卡号
     * @return 陪护证列表
     */
    List<EscortCard> QueryHelperEscorts(String helperCardNo);

    /**
     * 查询患者住院证关联的陪护证列表
     * 
     * @param patientCardNo 患者卡号
     * @param happenNo      住院证编号
     * @return
     */
    List<EscortCard> QueryPatientEscorts(String patientCardNo, Integer happenNo);

    /**
     * 查询陪护历史
     * 
     * @param patientCardNo 患者卡号
     * @param happenNo      住院证编号
     * @param helperCardNo  陪护人卡号
     * @return 陪护证列表
     */
    List<EscortCard> QueryEscortHistorys(String patientCardNo, Integer happenNo, String helperCardNo);

    /**
     * 添加一个新的陪护证
     * 
     * @param escortCard
     */
    void InsertEscort(EscortCard escortCard);
}

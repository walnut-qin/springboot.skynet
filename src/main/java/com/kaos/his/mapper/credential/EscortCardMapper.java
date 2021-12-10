package com.kaos.his.mapper.credential;

import java.util.List;

import com.kaos.his.entity.credential.EscortCard;

import org.springframework.stereotype.Repository;

@Repository
public interface EscortCardMapper {
    /**
     * 获取陪护证
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    EscortCard QueryEscort(String escortNo);

    /**
     * 根据陪护人卡号查询关联的陪护证
     * 
     * @param escortNo 陪护人卡号
     * @return
     */
    List<EscortCard> QueryHelperEscorts(String helperCardNo);

    /**
     * 根据患者卡号查询关联的陪护证
     * 
     * @param cardNo
     * @return
     */
    List<EscortCard> QueryPatientEscorts(String patientCardNo, Integer happenNo);

    /**
     * 指定陪护人和患者，查询历史陪护证
     * 
     * @param patientCardNo
     * @param happenNo
     * @param helperCardNo
     * @return
     */
    List<EscortCard> QueryHistoryEscorts(String patientCardNo, Integer happenNo, String helperCardNo);

    /**
     * 更新陪护证的状态
     * 
     * @param escortNo 陪护证编号
     * @param newState 新状态
     */
    void InsertEscortState(EscortCard.EscortState newState);

    /**
     * 更新陪护证的状态
     * 
     * @param escortNo 陪护证编号
     * @param newState 新状态
     */
    void InsertEscortStates(List<EscortCard.EscortState> newStates);

    /**
     * 更新陪护证的状态
     * 
     * @param escortNo 陪护证编号
     * @param newState 新状态
     */
    void InsertEscortAction(EscortCard.EscortAction newAction);

    /**
     * 更新陪护证的状态
     * 
     * @param escortNo 陪护证编号
     * @param newState 新状态
     */
    void InsertEscortActions(List<EscortCard.EscortAction> newActions);

    /**
     * 添加一个新的陪护证
     * 
     * @param escortCard
     */
    void InsertEscort(EscortCard escortCard);

    /**
     * 生成一个新的陪护证号
     * 
     * @return
     */
    String GenerateNewEscortNo();
}

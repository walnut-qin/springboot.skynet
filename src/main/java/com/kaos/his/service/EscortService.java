package com.kaos.his.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.credential.PreinCard;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.mapper.credential.EscortCardMapper;
import com.kaos.his.mapper.credential.PreinCardMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;
import com.kaos.his.mapper.personnel.PatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 陪护证系统的接口
 */
@Service
public class EscortService {
    /**
     * 陪护人实体接口
     */
    @Autowired
    EscortCardMapper escortMapper;

    /**
     * 住院证实体接口
     */
    @Autowired
    PreinCardMapper preinCardMapper;

    /**
     * 住院患者实体接口
     */
    @Autowired
    InpatientMapper inpatientMapper;

    /**
     * 患者实体接口
     */
    @Autowired
    PatientMapper patientMapper;

    /**
     * 根据陪护人卡号，查询有效的陪护证
     * 
     * @param helperCardNo 陪护卡号
     * @return 键值对列表<陪护证实体，住院实体>
     */
    public List<EscortCard> QueryActiveHelperEscorts(String helperCardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.QueryHelperEscorts(helperCardNo);

        // 辅助字典 - 记录患者的最近一次住院证，加速大批量数据时的查询
        Map<String, PreinCard> preinCardDict = new HashMap<String, PreinCard>();

        // 筛选出有效的陪护证
        for (EscortCard escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 获取陪护证关联的住院证
            escort.preinCard = Optional
                    .ofNullable(this.preinCardMapper.QueryPreinCard(escort.preinCard.cardNo, escort.preinCard.happenNo))
                    .orElse(escort.preinCard);

            // 获取患者最近的住院证
            PreinCard latestPreinCard = null;
            if (preinCardDict.containsKey(escort.preinCard.cardNo)) {
                latestPreinCard = preinCardDict.get(escort.preinCard.cardNo);
            } else {
                latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(escort.preinCard.cardNo);
                preinCardDict.put(escort.preinCard.cardNo, latestPreinCard);
            }

            // 如果住院证不是患者最近的一张陪护证，说明被关联的患者已经出院，陪护证应当自动失效
            if (escort.preinCard.happenNo != latestPreinCard.happenNo) {
                continue;
            }

            // 如果已入院，则将住院患者实体更新为住院实体，否则记录患者信息
            var inpatient = this.inpatientMapper.QueryInpatientR1(escort.preinCard.cardNo, escort.preinCard.happenNo);
            if (inpatient != null) {
                escort.preinCard.patient = inpatient;
            } else {
                escort.preinCard.patient = this.patientMapper.QueryPatient(escort.preinCard.cardNo);
            }

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }

    /**
     * 根据患者卡号查询关联陪护证
     * 
     * @param patientCardNo 患者就诊卡号
     * @return 陪护证实体
     */
    public List<EscortCard> QueryActivePatientEscorts(String patientCardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 获取最近的一张住院证
        var latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);
        if (latestPreinCard == null) {
            throw new RuntimeException("患者无住院证");
        }

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.QueryPatientEscorts(patientCardNo, latestPreinCard.happenNo);

        // 筛选出有效的陪护证
        for (EscortCard escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 记录helper信息
            escort.helper = Optional.ofNullable(this.patientMapper.QueryPatient(escort.helper.cardNo))
                    .orElse(escort.helper);

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }
}

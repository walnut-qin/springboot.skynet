package com.kaos.his.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    PreinCardMapper hospitalizationCertificateMapper;

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
     * 根据陪护人卡号，查询有效的被陪护患者信息
     * 
     * @param cardNo 陪护人卡号
     * @return 键值对列表<陪护证实体，住院实体>
     */
    public List<EscortCard> QueryActiveEscortsByHelper(String cardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.QueryHelperEscorts(cardNo);

        // 辅助字典 - 记录患者的最近一次住院证，加速大批量数据时的查询
        Map<String, PreinCard> hosCtfDict = new HashMap<String, PreinCard>();

        // 筛选出有效的陪护证
        for (EscortCard escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 提取住院证，如果住院证不是患者最近的一张陪护证，说明被关联的患者已经出院，陪护证应当自动失效
            var relatehosCtf = escort.hospitalizationCertificate;
            PreinCard latestHosCtf = null;
            if (hosCtfDict.containsKey(relatehosCtf.cardNo)) {
                latestHosCtf = hosCtfDict.get(relatehosCtf.cardNo);
            } else {
                latestHosCtf = this.hospitalizationCertificateMapper.QueryLatestPreinCard(relatehosCtf.cardNo);
                hosCtfDict.put(relatehosCtf.cardNo, latestHosCtf);
            }
            if (relatehosCtf.happenNo != latestHosCtf.happenNo) {
                continue;
            }

            // 如果已入院，则将住院患者实体更新为住院实体
            var inpatient = this.inpatientMapper.QueryInpatientR1(relatehosCtf.cardNo, relatehosCtf.happenNo);
            if (inpatient != null) {
                escort.hospitalizationCertificate.patient = inpatient;
            }

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }

    /**
     * 查询患者关联的有效的陪护证
     * 
     * @param cardNo 患者就诊卡号
     * @return 陪护证实体
     */
    public List<EscortCard> QueryActiveEscortsByPatient(String cardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 获取最近的一张住院证
        var latestHosCtf = this.hospitalizationCertificateMapper.QueryLatestPreinCard(cardNo);
        if (latestHosCtf == null) {
            throw new RuntimeException("患者无住院证");
        }

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.QueryPatientEscorts(cardNo, latestHosCtf.happenNo);

        // 筛选出有效的陪护证
        for (EscortCard escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }
}

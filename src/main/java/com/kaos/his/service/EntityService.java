package com.kaos.his.service;

import java.util.ArrayList;
import java.util.List;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.mapper.credential.EscortCardMapper;
import com.kaos.his.mapper.credential.PreinCardMapper;
import com.kaos.his.mapper.personnel.PatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityService {
    /**
     * 患者实体接口
     */
    @Autowired
    PatientMapper patientMapper;

    /**
     * 住院证实体接口
     */
    @Autowired
    PreinCardMapper preinCardMapper;

    /**
     * 陪护证实体接口
     */
    @Autowired
    EscortCardMapper escortCardMapper;

    /**
     * 根据卡号获取患者实体
     * 
     * @param cardNo
     * @return
     */
    public Patient QueryPatient(String cardNo) {
        return this.patientMapper.QueryPatient(cardNo);
    }

    /**
     * 查询所有有效的陪护证
     * 
     * @param patientCardNo
     * @return
     */
    public List<EscortCard> QueryActiveEscorts(String patientCardNo) {
        // 获取最近的一张住院证
        var preinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);
        if (preinCard == null) {
            return null;
        }

        // 查询所有关联陪护证
        var escorts = this.escortCardMapper.QueryPatientEscorts(preinCard.cardNo, preinCard.happenNo);

        // 过滤掉无效的陪护证
        List<EscortCard> rs = new ArrayList<>();
        for (EscortCard escortCard : escorts) {
            if (escortCard.states.size() == 0) {
                continue;
            } else if (escortCard.states.get(escortCard.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            } else {
                // 补全关联的陪护人信息
                escortCard.helper = this.patientMapper.QueryPatient(escortCard.helperCardNo);

                // 加入结果集
                rs.add(escortCard);
            }
        }

        return rs;
    }
}

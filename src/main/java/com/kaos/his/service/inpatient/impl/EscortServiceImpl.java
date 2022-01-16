package com.kaos.his.service.inpatient.impl;

import java.util.ArrayList;
import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.InpatientStateEnum;
import com.kaos.his.mapper.common.PatientMapper;
import com.kaos.his.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.his.mapper.inpatient.InpatientMapper;
import com.kaos.his.mapper.inpatient.escort.EscortActionRecMapper;
import com.kaos.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.his.service.inpatient.EscortService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EscortServiceImpl implements EscortService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(DayReportServiceImpl.class.getName());

    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护证状态接口
     */
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    /**
     * 陪护证动作接口
     */
    @Autowired
    EscortActionRecMapper escortActionRecMapper;

    /**
     * 住院证接口
     */
    @Autowired
    FinIprPrepayInMapper finIprPrepayInMapper;

    /**
     * 住院接口
     */
    @Autowired
    InpatientMapper inpatientMapper;

    /**
     * 患者接口
     */
    @Autowired
    PatientMapper patientMapper;

    @Override
    public EscortMainInfo queryEscortStateInfo(String escortNo) {
        // 检索陪护证
        var rt = this.escortMainInfoMapper.queryEscortMainInfo(escortNo);
        if (rt == null) {
            return null;
        }

        // 添加关联状态清单
        rt.associateEntity.stateRecs = this.escortStateRecMapper.queryStates(escortNo);

        return rt;
    }

    @Override
    public List<EscortMainInfo> queryPatientInfos(String helperCardNo) {
        // 检索关联的陪护证实体
        var rs = this.escortMainInfoMapper.queryHelperEscortMainInfos(helperCardNo, new ArrayList<>() {
            {
                add(EscortStateEnum.无核酸检测结果);
                add(EscortStateEnum.等待院内核酸检测结果);
                add(EscortStateEnum.等待院外核酸检测结果审核);
                add(EscortStateEnum.生效中);
            }
        });

        for (var rt : rs) {
            // 填充装填实体
            rt.associateEntity.stateRecs = this.escortStateRecMapper.queryStates(rt.escortNo);

            // 填充动作实体
            rt.associateEntity.actionRecs = this.escortActionRecMapper.queryActions(rt.escortNo);

            // 填充患者信息
            rt.associateEntity.finIprPrepayIn = this.finIprPrepayInMapper.queryPrepayIn(rt.patientCardNo, rt.happenNo);
            if (rt.associateEntity.finIprPrepayIn != null) {
                // 锚定住院证
                var fip = rt.associateEntity.finIprPrepayIn;

                // 检索住院实体
                var inpatients = this.inpatientMapper.queryInpatients(rt.patientCardNo, rt.happenNo, new ArrayList<>() {
                    {
                        add(InpatientStateEnum.住院登记);
                        add(InpatientStateEnum.病房接诊);
                        add(InpatientStateEnum.出院登记);
                        add(InpatientStateEnum.预约出院);
                    }
                });
                switch (inpatients.size()) {
                    case 0:
                        fip.associateEntity.patient = this.patientMapper.queryPatient(rt.patientCardNo);
                        break;

                    case 1:
                        fip.associateEntity.patient = inpatients.get(0);
                        break;

                    default:
                        fip.associateEntity.patient = this.patientMapper.queryPatient(rt.patientCardNo);
                        break;
                }
            }
        }
        return null;
    }
}

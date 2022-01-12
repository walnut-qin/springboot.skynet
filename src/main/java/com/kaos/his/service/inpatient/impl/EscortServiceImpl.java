package com.kaos.his.service.inpatient.impl;

import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
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

    /**
     * 查询陪护证
     */
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
}

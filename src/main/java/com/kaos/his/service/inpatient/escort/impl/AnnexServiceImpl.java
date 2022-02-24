package com.kaos.his.service.inpatient.escort.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortAnnexChk;
import com.kaos.his.entity.inpatient.escort.EscortAnnexInfo;
import com.kaos.his.enums.inpatient.InStateEnum;
import com.kaos.his.enums.inpatient.escort.EscortStateEnum;
import com.kaos.his.mapper.common.ComPatientInfoMapper;
import com.kaos.his.mapper.inpatient.InpatientMapper;
import com.kaos.his.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.his.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.his.service.inpatient.escort.AnnexService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class AnnexServiceImpl implements AnnexService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(AnnexServiceImpl.class);

    /**
     * 患者接口
     */
    @Autowired
    ComPatientInfoMapper patientMapper;

    /**
     * 住院接口
     */
    @Autowired
    InpatientMapper inpatientMapper;

    /**
     * 附件接口
     */
    @Autowired
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    /**
     * 审核附件接口
     */
    @Autowired
    EscortAnnexChkMapper escortAnnexChkMapper;

    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    @Override
    public EscortAnnexInfo uploadAnnex(String helperCardNo, String url) {
        // 创建实体
        var annexInfo = new EscortAnnexInfo() {
            {
                annexNo = null;
                cardNo = helperCardNo;
                annexUrl = url;
                recDate = new Date();
            }
        };

        // 插入记录
        this.escortAnnexInfoMapper.insertAnnexInfo(annexInfo);

        return annexInfo;
    }

    @Override
    public EscortAnnexChk checkAnnex(String annexNo, String checker, Boolean negativeFlag, Date inspectDate) {
        // 创建实体
        var annexChk = new EscortAnnexChk();
        annexChk.annexNo = annexNo;
        annexChk.chkEmplCode = checker;
        annexChk.chkDate = new Date();
        annexChk.negativeFlag = negativeFlag;
        annexChk.inspectDate = inspectDate;

        // 插入记录
        this.escortAnnexChkMapper.insertAnnexChk(annexChk);

        return annexChk;
    }

    @Override
    public List<EscortAnnexInfo> queryAnnexInfoInDept(String deptCode, Boolean checked) {
        // 辅助结果集<附件Id，信息>
        HashMap<String, EscortAnnexInfo> rs = new HashMap<>();

        // 查询该科室的所有患者
        var inpatients = this.inpatientMapper.queryInpatients(null, deptCode, new ArrayList<>() {
            {
                add(InStateEnum.住院登记);
                add(InStateEnum.病房接诊);
            }
        });
        for (var inpatient : inpatients) {
            // 患者关联的陪护证
            var escorts = this.escortMainInfoMapper.queryEscortMainInfos(inpatient.cardNo, inpatient.happenNo, null,
                    new ArrayList<>() {
                        {
                            add(EscortStateEnum.无核酸检测结果);
                            add(EscortStateEnum.等待院内核酸检测结果);
                            add(EscortStateEnum.等待院外核酸检测结果审核);
                            add(EscortStateEnum.生效中);
                        }
                    });
            for (var escort : escorts) {
                // 附件内容
                var annexs = this.escortAnnexInfoMapper.queryAnnexInfos(escort.helperCardNo, null, null, checked);
                for (var annex : annexs) {
                    if (rs.keySet().contains(annex.annexNo)) {
                        if (annex.associateEntity.patient != null) {
                            annex.associateEntity.patient.associateEntity.escortedPatients.add(inpatient);
                        }
                    } else {
                        annex.associateEntity.patient = this.patientMapper.queryPatientInfo(escort.helperCardNo);
                        if (annex.associateEntity.patient != null) {
                            annex.associateEntity.patient.associateEntity.escortedPatients = new ArrayList<>();
                            annex.associateEntity.patient.associateEntity.escortedPatients.add(inpatient);
                        }
                        annex.associateEntity.escortAnnexChk = this.escortAnnexChkMapper.queryAnnexChk(annex.annexNo);
                        rs.put(annex.annexNo, annex);
                    }
                }
            }
        }

        return rs.values().stream().toList();
    }
}

package com.kaos.skynet.api.service.impl.inpatient.escort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.kaos.skynet.api.cache.impl.common.ComPatientInfoCache;
import com.kaos.skynet.api.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.service.inf.inpatient.escort.AnnexService;
import com.kaos.skynet.entity.inpatient.escort.EscortAnnexChk;
import com.kaos.skynet.entity.inpatient.escort.EscortAnnexInfo;
import com.kaos.skynet.enums.impl.inpatient.InStateEnum;
import com.kaos.skynet.enums.impl.inpatient.escort.EscortStateEnum;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnexServiceImpl implements AnnexService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(AnnexServiceImpl.class);

    /**
     * 住院接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

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

    /**
     * 患者接口
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    @Override
    @Transactional
    public EscortAnnexInfo uploadAnnex(String helperCardNo, String url) {
        // 创建实体
        var annexInfo = new EscortAnnexInfo() {
            {
                annexNo = null;
                cardNo = helperCardNo;
                annexUrl = url;
                recDate = LocalDateTime.now();
            }
        };

        // 插入记录
        this.escortAnnexInfoMapper.insertAnnexInfo(annexInfo);

        return annexInfo;
    }

    @Override
    @Transactional
    public EscortAnnexChk checkAnnex(String annexNo, String checker, Boolean negativeFlag, LocalDateTime inspectDate) {
        // 检索审核记录
        var chk = this.escortAnnexChkMapper.queryAnnexChk(annexNo);
        if (chk != null) {
            throw new RuntimeException("附件已被审核");
        }

        // 创建实体
        var annexChk = new EscortAnnexChk();
        annexChk.annexNo = annexNo;
        annexChk.chkEmplCode = checker;
        annexChk.chkDate = LocalDateTime.now();
        annexChk.negativeFlag = negativeFlag;
        annexChk.inspectDate = inspectDate;

        // 插入记录
        this.escortAnnexChkMapper.insertAnnexChk(annexChk);

        return annexChk;
    }

    @Override
    public List<EscortAnnexInfo> queryAnnexInfoInDept(String deptCode, Boolean checked) {
        // 查询该科室的所有患者
        var inMainInfos = this.inMainInfoMapper.queryInpatients(null, null, deptCode, new ArrayList<>() {
            {
                add(InStateEnum.住院登记);
                add(InStateEnum.病房接诊);
            }
        });

        // 检索患者关联的所有陪护人<陪护人卡号, 患者卡号>
        Multimap<String, String> helperCardNos = HashMultimap.create();
        for (var inMainInfo : inMainInfos) {
            // 患者关联的陪护证
            var escorts = this.escortMainInfoMapper.queryEscortMainInfos(inMainInfo.cardNo, inMainInfo.happenNo, null,
                    new ArrayList<>() {
                        {
                            add(EscortStateEnum.无核酸检测结果);
                            add(EscortStateEnum.等待院内核酸检测结果);
                            add(EscortStateEnum.等待院外核酸检测结果审核);
                            add(EscortStateEnum.生效中);
                        }
                    });
            for (var escort : escorts) {
                helperCardNos.put(escort.helperCardNo, inMainInfo.cardNo);
            }
        }

        // 检索附件
        List<EscortAnnexInfo> annexs = Lists.newArrayList();
        for (var helperCardNo : helperCardNos.keySet()) {
            // 检索所有附件
            var subAnnexs = this.escortAnnexInfoMapper.queryAnnexInfos(helperCardNo, null, null, checked);
            // 补全信息
            for (var subAnnex : subAnnexs) {
                subAnnex.associateEntity.patientInfo = this.patientInfoCache.getValue(helperCardNo);
                if (subAnnex.associateEntity.patientInfo != null) {
                    subAnnex.associateEntity.patientInfo.associateEntity.escortedPatients = helperCardNos
                            .get(helperCardNo).stream().map(x -> {
                                return patientInfoCache.getValue(x);
                            }).toList();
                }
                if (checked) {
                    subAnnex.associateEntity.escortAnnexChk = this.escortAnnexChkMapper.queryAnnexChk(subAnnex.annexNo);
                }
            }
            annexs.addAll(subAnnexs);
        }

        return annexs;
    }
}

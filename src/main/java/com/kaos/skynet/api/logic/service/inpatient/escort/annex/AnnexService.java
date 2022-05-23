package com.kaos.skynet.api.logic.service.inpatient.escort.annex;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexCheckCache;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexCheck;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.mapper.common.SequenceMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.annex.EscortAnnexCheckMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.annex.EscortAnnexInfoMapper;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class AnnexService {
    /**
     * 序列查询器
     */
    @Autowired
    SequenceMapper sequenceMapper;

    /**
     * 附件信息接口
     */
    @Autowired
    EscortAnnexInfoMapper annexInfoMapper;

    /**
     * 附件审核接口
     */
    @Autowired
    EscortAnnexCheckMapper annexCheckMapper;

    /**
     * 审核缓存
     */
    @Autowired
    EscortAnnexCheckCache.MasterCache annexCheckMasterCache;

    /**
     * 审核缓存
     */
    @Autowired
    EscortAnnexCheckCache.SlaveCache annexCheckSlaveCache;

    /**
     * 上传附件
     * 
     * @param cardNo 就诊卡号
     * @param url    附件外链
     * @return
     */
    @Transactional
    public String uploadAnnex(String cardNo, String url) {
        // 生成待插入对象
        EscortAnnexInfo annexInfo = new EscortAnnexInfo() {
            {
                setAnnexNo(StringUtils.leftPad(sequenceMapper.query("KAOS.SEQ_ANNEX_NO"), 10, '0'));
                setCardNo(cardNo);
                setAnnexUrl(url);
                setOperDate(LocalDateTime.now());
            }
        };

        try {
            // 插入数据库
            annexInfoMapper.insertAnnexInfo(annexInfo);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return annexInfo.getAnnexNo();
    }

    /**
     * 审核附件
     * 
     * @param annexNo      附件ID
     * @param checker      审核人
     * @param negativeFlag 阴性标识
     * @param inspectDate  核酸检测时间
     */
    @Transactional
    public void checkAnnex(String annexNo, String checker, Boolean negativeFlag, LocalDateTime inspectDate) {
        // 检索审核记录
        EscortAnnexCheck annexCheck = annexCheckMasterCache.get(annexNo);

        // 更新数据库
        if (annexCheck == null) {
            // 构造待插入对象
            annexCheck = new EscortAnnexCheck() {
                {
                    setAnnexNo(annexNo);
                    setOperCode(checker);
                    setOperDate(LocalDateTime.now());
                    setNegative(negativeFlag);
                    setInspectDate(inspectDate);
                }
            };

            // 插入对象
            annexCheckMapper.insertAnnexCheck(annexCheck);
        } else {
            // 更新审核内容
            annexCheck.setOperCode(checker);
            annexCheck.setOperDate(LocalDateTime.now());
            annexCheck.setNegative(negativeFlag);
            annexCheck.setInspectDate(inspectDate);

            // 更新至数据库
            annexCheckMapper.updateAnnexCheck(annexCheck);
        }

        // 更新缓存
        annexCheckMasterCache.refresh(annexNo);
    }
}

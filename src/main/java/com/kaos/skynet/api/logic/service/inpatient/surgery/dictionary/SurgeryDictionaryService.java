package com.kaos.skynet.api.logic.service.inpatient.surgery.dictionary;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class SurgeryDictionaryService {
    /**
     * 手术字典接口
     */
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    /**
     * 添加新手术字典
     * 
     * @param icdCode
     * @param name
     * @param level
     * @param teleprompter
     */
    @Transactional
    public void insertSurgeryDict(String icdCode, String name, SurgeryLevelEnum level, String teleprompter) {
        // 检索手术字典
        var surgeryDict = surgeryDictMapper.querySurgeryDict(icdCode);
        if (surgeryDict != null) {
            log.error("手术已存在, 无法添加, ICD-9 = ".concat(icdCode));
            throw new RuntimeException("手术已存在");
        }

        // 构造新对象
        var builder = SurgeryDict.builder();
        builder.icdCode(icdCode);
        builder.surgeryName(name);
        builder.surgeryLevel(level);
        builder.valid(true);
        builder.operDate(LocalDateTime.now());
        builder.teleprompter(teleprompter);

        // 插入数据库
        surgeryDictMapper.insertSurgeryDict(builder.build());
    }

    /**
     * 修改新手术字典
     * 
     * @param icdCode
     * @param name
     * @param level
     * @param teleprompter
     */
    @Transactional
    public void updateSurgeryDict(String icdCode, String name, SurgeryLevelEnum level, String teleprompter) {
        // 检索手术字典
        var surgeryDict = surgeryDictMapper.querySurgeryDict(icdCode);
        if (surgeryDict == null) {
            log.error("手术不存在, 无法修改, ICD-9 = ".concat(icdCode));
            throw new RuntimeException("手术不存在");
        }

        // 修改对象
        surgeryDict.setSurgeryName(name);
        surgeryDict.setSurgeryLevel(level);
        surgeryDict.setValid(true);
        surgeryDict.setOperDate(LocalDateTime.now());
        surgeryDict.setTeleprompter(teleprompter);

        // 同步到数据库
        surgeryDictMapper.updateSurgeryDict(surgeryDict);
    }

    /**
     * 修改新手术字典
     * 
     * @param icdCode
     * @param name
     * @param level
     * @param teleprompter
     */
    @Transactional
    public void deleteSurgeryDict(String icdCode) {
        // 检索手术字典
        var surgeryDict = surgeryDictMapper.querySurgeryDict(icdCode);
        if (surgeryDict == null) {
            return;
        }

        // 敏感操作记录日志
        log.warn("删除手术字典, ICD-9 = ".concat(icdCode));

        // 同步到数据库
        surgeryDictMapper.deleteSurgeryDict(icdCode);
    }
}

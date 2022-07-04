package com.kaos.skynet.api.logic.service.inpatient.surgery.dictionary;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (surgeryDict == null) {
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
            return;
        }

        // 存在旧记录，修改旧记录
        surgeryDict.setSurgeryName(name);
        surgeryDict.setSurgeryLevel(level);
        surgeryDict.setValid(true);
        surgeryDict.setOperDate(LocalDateTime.now());
        surgeryDict.setTeleprompter(teleprompter);
        surgeryDictMapper.updateSurgeryDict(surgeryDict);
    }
}

package com.kaos.his.service;

import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.mapper.personnel.PatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseInfoService {
    /**
     * 患者信息接口
     */
    @Autowired
    PatientMapper patientMapper;

    /**
     * 查询指定患者信息
     * 
     * @param cardNo
     * @return
     */
    public Patient QueryPatient(String cardNo) {
        // 查询指定实体
        return this.patientMapper.QueryPatient(cardNo);
    }
}

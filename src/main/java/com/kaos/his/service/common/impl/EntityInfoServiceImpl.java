package com.kaos.his.service.common.impl;

import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.mapper.common.ComPatientInfoMapper;
import com.kaos.his.service.common.EntityInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityInfoServiceImpl implements EntityInfoService {
    @Autowired
    ComPatientInfoMapper patientMapper;

    @Override
    public ComPatientInfo queryPatient(String cardNo) {
        return this.patientMapper.queryPatientInfo(cardNo);
    }
}

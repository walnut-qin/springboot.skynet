package com.kaos.his.service.common.impl;

import com.kaos.his.entity.common.Patient;
import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.common.PatientMapper;
import com.kaos.his.service.common.EntityInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntityInfoServiceImpl implements EntityInfoService {
    @Autowired
    PatientMapper patientMapper;

    @Override
    public Patient queryPatient(String cardNo) {
        return this.patientMapper.queryPatient(cardNo, ValidStateEnum.有效);
    }
}

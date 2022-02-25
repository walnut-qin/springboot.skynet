package com.kaos.his.service.impl.common;

import com.kaos.his.entity.common.ComPatientInfo;
import com.kaos.his.mapper.common.ComPatientInfoMapper;
import com.kaos.his.service.inf.common.PatientInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientInfoServiceImpl implements PatientInfoService {
    @Autowired
    ComPatientInfoMapper patientMapper;

    @Override
    public ComPatientInfo queryPatientInfo(String cardNo) {
        return this.patientMapper.queryPatientInfo(cardNo);
    }
}

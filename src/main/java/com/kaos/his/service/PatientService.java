package com.kaos.his.service;

import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.mapper.PatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientMapper patientMapper;

    public Patient GetPatientByCardNo(String cardNo) {
        return patientMapper.GetPatientByCardNo(cardNo);
    }
}

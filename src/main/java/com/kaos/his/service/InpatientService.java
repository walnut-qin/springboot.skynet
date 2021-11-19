package com.kaos.his.service;

import java.util.List;

import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.mapper.personnel.InpatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InpatientService {
    @Autowired
    private InpatientMapper inpatientMapper;

    public Inpatient GetInpatientByPatientNo(String patientNo) {
        return this.inpatientMapper.GetInpatientByPatientNo(patientNo);
    }

    public List<Inpatient> GetInpatientsByCardNo(String cardNo) {
        return this.inpatientMapper.GetInpatientsByCardNo(cardNo);
    }
}

package com.kaos.his.service;

import com.kaos.his.entity.personnel.Outpatient;
import com.kaos.his.mapper.OutpatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutpatientService {
    @Autowired
    private OutpatientMapper outpatientMapper;

    public Outpatient GetOutpatientByClinicCode(String clinicCode) {
        return this.outpatientMapper.GetOutpatientByClinicCode(clinicCode);
    }
}

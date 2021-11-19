package com.kaos.his.service;

import java.util.List;

import com.kaos.his.entity.personnel.Outpatient;
import com.kaos.his.mapper.personnel.OutpatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OutpatientService {
    @Autowired
    private OutpatientMapper outpatientMapper;

    public Outpatient GetOutpatientByClinicCode(String clinicCode) {
        return this.outpatientMapper.GetOutpatientByClinicCode(clinicCode);
    }

    public List<Outpatient> GetOutpatientsByCardNo(String cardNo) {
        return this.outpatientMapper.GetOutpatientsByCardNo(cardNo);
    }
}

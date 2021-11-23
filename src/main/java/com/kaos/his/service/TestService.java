package com.kaos.his.service;

import com.kaos.his.entity.personnel.Inpatient;
import com.kaos.his.mapper.lis.NucleicAcidTestMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    InpatientMapper inpatientMapper;

    @Autowired
    NucleicAcidTestMapper nucleicAcidTestMapper;

    public Inpatient Run(String param) {
        var v = this.inpatientMapper.QueryInpatient(param);
        v.nucleicAcidTests = this.nucleicAcidTestMapper.QueryNucleicAcidTest(v.patientNo, 14);
        return v;
    }
}

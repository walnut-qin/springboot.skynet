package com.kaos.his.service;

import java.util.List;

import com.kaos.his.entity.lis.NucleicAcidTest;
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

    public List<NucleicAcidTest> Run(String param) {
        return this.nucleicAcidTestMapper.QueryNucleicAcidTest(param, "nCOV-RNA", 14);
    }
}

package com.kaos.his.service;

import com.kaos.his.entity.drug.DrugBaseInfo;
import com.kaos.his.mapper.drug.DrugBaseInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    DrugBaseInfoMapper drugBaseInfoMapper;

    @Transactional
    public DrugBaseInfo Run(String param) {
        var escort = this.drugBaseInfoMapper.QueryDrugBaseInfo(param);

        return escort;
    }
}

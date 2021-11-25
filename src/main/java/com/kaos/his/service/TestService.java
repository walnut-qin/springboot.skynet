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
        // 查询出指定的药品实体
        var drug = this.drugBaseInfoMapper.QueryDrugBaseInfo("Y00000017445");

        // 替换主键
        drug.drugCode = "KaOS";

        // 回写
        this.drugBaseInfoMapper.UpdateDrugBaseInfo(drug);

        return drug;
    }
}

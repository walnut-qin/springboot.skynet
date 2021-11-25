package com.kaos.his.mapper.drug;

import com.kaos.his.entity.drug.DrugBaseInfo;

import org.springframework.stereotype.Repository;

@Repository
public interface DrugBaseInfoMapper {
    /**
     * 主键查询药品账页
     * 
     * @param drugCode 药品编码
     * @return
     */
    DrugBaseInfo QueryDrugBaseInfo(String drugCode);
}

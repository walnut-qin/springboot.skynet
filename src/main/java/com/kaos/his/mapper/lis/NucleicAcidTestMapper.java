package com.kaos.his.mapper.lis;

import java.util.List;

import com.kaos.his.entity.lis.NucleicAcidTest;

import org.springframework.stereotype.Repository;

@Repository
public interface NucleicAcidTestMapper {
    /**
     * 查询核酸检测结果
     * 
     * @param cardNo 卡号
     * @param days   多少日以内
     * @return
     */
    List<NucleicAcidTest> QueryNucleicAcidTest(String cardNo, Integer days);
}

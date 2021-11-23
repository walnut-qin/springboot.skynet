package com.kaos.his.mapper.lis;

import java.util.List;

import com.kaos.his.entity.lis.NucleicAcidTest;

import org.springframework.stereotype.Repository;

@Repository
public interface NucleicAcidTestMapper {
    /**
     * 查询核酸检测结果
     * 
     * @param index    索引值
     * @param itemCode 项目编码
     * @param days     检索期限
     * @return 检测结果实体
     */
    List<NucleicAcidTest> QueryNucleicAcidTest(String index, String itemCode, Integer days);
}

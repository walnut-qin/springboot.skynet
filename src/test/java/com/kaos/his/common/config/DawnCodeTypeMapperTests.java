package com.kaos.his.common.config;

import com.kaos.his.mapper.common.config.DawnCodeTypeMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DawnCodeTypeMapperTests {
    @Autowired
    DawnCodeTypeMapper dawnCodeTypeMapper;

    @Test
    public void queryDawnCodeType() {
        this.dawnCodeTypeMapper.queryDawnCodeType(null);
        this.dawnCodeTypeMapper.queryDawnCodeType("DrugHedging");
    }
}

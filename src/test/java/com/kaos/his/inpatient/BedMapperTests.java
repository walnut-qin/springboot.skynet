package com.kaos.his.inpatient;

import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.inpatient.BedMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BedMapperTests {
    @Autowired
    BedMapper bedMapper;

    @Test
    public void queryBed() {
        this.bedMapper.queryBed("901985", null);
        this.bedMapper.queryBed("901985", ValidStateEnum.作废);
        this.bedMapper.queryBed("901985", ValidStateEnum.无效);
        this.bedMapper.queryBed("901985", ValidStateEnum.有效);
    }
}

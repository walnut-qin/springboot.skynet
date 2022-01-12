package com.kaos.his.inpatient;

import com.kaos.his.enums.ValidStateEnum;
import com.kaos.his.mapper.inpatient.ComBedInfoMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BedMapperTests {
    @Autowired
    ComBedInfoMapper bedMapper;

    @Test
    public void queryBed() {
        this.bedMapper.queryBedInfo("901985", null);
        this.bedMapper.queryBedInfo("901985", ValidStateEnum.作废);
        this.bedMapper.queryBedInfo("901985", ValidStateEnum.无效);
        this.bedMapper.queryBedInfo("901985", ValidStateEnum.有效);
    }
}

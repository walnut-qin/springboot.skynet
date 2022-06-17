package com.kaos.skynet.api.data.his.mapper.inpatient.escort;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortMainInfoMapperTests {
    @Autowired
    EscortMainInfoMapper mainInfoMapper;

    @Test
    void queryEscortMainInfo() {
        mainInfoMapper.queryEscortMainInfo("0000001094");
    }

    @Test
    void queryEscortMainInfos() {
        mainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNo("test")
                .happenNo(10)
                .helperCardNo("0123456789")
                .states(Lists.newArrayList(StateEnum.注销))
                .build());
    }
}

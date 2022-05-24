package com.kaos.skynet.api.data.mapper.inpatient.escort;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;

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
                .patientCardNos(Lists.newArrayList("2000003605"))
                .happenNo(10)
                .helperCardNo("0123456789")
                .states(Lists.newArrayList(EscortStateEnum.注销)).build());
    }
}

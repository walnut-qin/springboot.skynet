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
        mainInfoMapper.queryEscortMainInfos(new EscortMainInfoMapper.Key() {
            {
                setPatientCardNos(Lists.newArrayList("2000003605"));
                setHappenNo(10);
                setHelperCardNo("0123456789");
                setStates(Lists.newArrayList(EscortStateEnum.注销));
            }
        });
    }
}

package com.kaos.his.inpatient.escort;

import java.util.ArrayList;

import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.mapper.inpatient.escort.EscortMainInfoMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortMainInfoMapperTests {
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    @Test
    public void queryEscortMainInfo() {
        this.escortMainInfoMapper.queryEscortMainInfo("0000000018");
    }

    @Test
    public void queryPatientEscortMainInfos() {
        this.escortMainInfoMapper.queryPatientEscortMainInfos("2000003605", 10, null);
        this.escortMainInfoMapper.queryPatientEscortMainInfos("2000003605", 10, new ArrayList<EscortStateEnum>() {
            {
                add(EscortStateEnum.无核酸检测结果);
            }
        });
    }

    @Test
    public void queryHelperEscortMainInfos() {
        this.escortMainInfoMapper.queryHelperEscortMainInfos("2000003605", null);
        this.escortMainInfoMapper.queryHelperEscortMainInfos("2000003605", new ArrayList<EscortStateEnum>() {
            {
                add(EscortStateEnum.无核酸检测结果);
            }
        });
    }
}

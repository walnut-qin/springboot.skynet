package com.kaos.skynet.api.mapper.inpatient.escort;

import java.util.ArrayList;

import com.kaos.skynet.enums.impl.inpatient.escort.EscortStateEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EscortMainInfoMapperTests {
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    @Test
    public void queryEscortMainInfo() {
        this.escortMainInfoMapper.queryEscortMainInfo(null);
        this.escortMainInfoMapper.queryEscortMainInfo("0000000018");
    }

    @Test
    public void queryEscortMainInfos() {
        this.escortMainInfoMapper.queryEscortMainInfos("2000003605", 10, null, null);
        this.escortMainInfoMapper.queryEscortMainInfos("2000003605", 10, null, new ArrayList<EscortStateEnum>() {
            {
                add(EscortStateEnum.无核酸检测结果);
            }
        });

        this.escortMainInfoMapper.queryEscortMainInfos(null, null, "2000003605", null);
        this.escortMainInfoMapper.queryEscortMainInfos(null, null, "2000003605", new ArrayList<EscortStateEnum>() {
            {
                add(EscortStateEnum.无核酸检测结果);
            }
        });

        this.escortMainInfoMapper.queryEscortMainInfos("2000003605", 10, "2000003605", null);
        this.escortMainInfoMapper.queryEscortMainInfos("2000003605", 10, "2000003605",
                new ArrayList<EscortStateEnum>() {
                    {
                        add(EscortStateEnum.无核酸检测结果);
                    }
                });
    }

    @Test
    public void queryLastEscortMainInfo() {
        this.escortMainInfoMapper.queryLastEscortMainInfo("2000003605", 10, null, null);
        this.escortMainInfoMapper.queryLastEscortMainInfo("2000003605", 10, null, new ArrayList<EscortStateEnum>() {
            {
                add(EscortStateEnum.无核酸检测结果);
            }
        });

        this.escortMainInfoMapper.queryLastEscortMainInfo(null, null, "2000003605", null);
        this.escortMainInfoMapper.queryLastEscortMainInfo(null, null, "2000003605", new ArrayList<EscortStateEnum>() {
            {
                add(EscortStateEnum.无核酸检测结果);
            }
        });

        this.escortMainInfoMapper.queryLastEscortMainInfo("2000003605", 10, "2000003605",
                new ArrayList<EscortStateEnum>() {
                    {
                        add(EscortStateEnum.无核酸检测结果);
                    }
                });
    }
}

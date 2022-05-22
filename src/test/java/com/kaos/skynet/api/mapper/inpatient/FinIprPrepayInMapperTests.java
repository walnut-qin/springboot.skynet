package com.kaos.skynet.api.mapper.inpatient;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.enums.inpatient.FinIprPrepayInStateEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprPrepayInMapperTests {
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    @Test
    public void queryPrepayIn() {
        this.prepayInMapper.queryPrepayIn("2000003605", 1);
    }

    @Test
    public void queryPrepayIns() {
        prepayInMapper.queryPrepayIns(new FinIprPrepayInMapper.Key() {
            {
                setCardNo("2000003605");
                setStates(Lists.newArrayList(FinIprPrepayInStateEnum.预约));
            }
        });

    }
}

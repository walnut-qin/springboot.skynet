package com.kaos.his.inpatient;

import java.util.ArrayList;

import com.kaos.his.enums.FinIprPrepayInStateEnum;
import com.kaos.his.mapper.inpatient.FinIprPrepayInMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIprPrepayInMapperTests {
    @Autowired
    FinIprPrepayInMapper finIprPrepayInMapper;

    @Test
    public void queryPrepayIn() {
        this.finIprPrepayInMapper.queryPrepayIn("2000003605", 1);
    }

    @Test
    public void queryPrepayIns() {
        this.finIprPrepayInMapper.queryPrepayIns("2000003605", new ArrayList<FinIprPrepayInStateEnum>() {
            {
                add(FinIprPrepayInStateEnum.预约);
            }
        });
    }
}

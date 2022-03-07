package com.kaos.skynet.mapper.inpatient;

import java.util.ArrayList;

import com.kaos.skynet.enums.impl.inpatient.FinIprPrepayInStateEnum;

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

    @Test
    public void queryLastPrepayIn() {
        this.finIprPrepayInMapper.queryLastPrepayIn("2000003605", null);
        this.finIprPrepayInMapper.queryLastPrepayIn("2000003605", new ArrayList<>() {
            {
                add(FinIprPrepayInStateEnum.预约);
                add(FinIprPrepayInStateEnum.转住院);
                add(FinIprPrepayInStateEnum.签床);
                add(FinIprPrepayInStateEnum.预住院预约);
            }
        });
    }
}

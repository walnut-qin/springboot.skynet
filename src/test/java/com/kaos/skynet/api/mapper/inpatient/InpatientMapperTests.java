package com.kaos.skynet.api.mapper.inpatient;

import java.util.ArrayList;

import com.kaos.skynet.api.enums.inpatient.InStateEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InpatientMapperTests {
    @Autowired
    InpatientMapper inpatientMapper;

    @Test
    public void queryInpatient() {
        this.inpatientMapper.queryInpatient("ZY010000705856");
    }

    @Test
    public void queryInpatientsWithPrepayIn() {
        this.inpatientMapper.queryInpatientsWithPrepayIn("2000003605", 10);
        this.inpatientMapper.queryInpatientsWithPrepayIn("2000003605", null);
    }

    @Test
    public void queryLastInpatient() {
        this.inpatientMapper.queryInpatients("2000003605", null, null);
        this.inpatientMapper.queryInpatients("2000003605", null, new ArrayList<>() {
            {
                add(InStateEnum.病房接诊);
            }
        });
    }
}

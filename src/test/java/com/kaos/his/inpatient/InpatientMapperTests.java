package com.kaos.his.inpatient;

import java.util.ArrayList;

import com.kaos.his.enums.inpatient.InpatientStateEnum;
import com.kaos.his.mapper.inpatient.InpatientMapper;

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
                add(InpatientStateEnum.病房接诊);
            }
        });
    }
}

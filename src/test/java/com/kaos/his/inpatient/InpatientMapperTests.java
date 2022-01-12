package com.kaos.his.inpatient;

import java.util.ArrayList;

import com.kaos.his.enums.InpatientStateEnum;
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
    public void queryInpatients() {
        this.inpatientMapper.queryInpatients("2551130556", null, null);
        this.inpatientMapper.queryInpatients("2551130556", 1, new ArrayList<InpatientStateEnum>() {
            {
                add(InpatientStateEnum.住院登记);
            }
        });
    }
}

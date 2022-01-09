package com.kaos.his.inpatient.surgery;

import java.util.ArrayList;

import com.kaos.his.enums.SurgeryArrangeRoleEnum;
import com.kaos.his.mapper.inpatient.surgery.MetOpsArrangeMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsArrangeTests {
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    @Test
    public void queryMetOpsArranges() {
        this.metOpsArrangeMapper.queryMetOpsArranges("742", new ArrayList<SurgeryArrangeRoleEnum>() {
            {
                add(SurgeryArrangeRoleEnum.Operator);
                add(SurgeryArrangeRoleEnum.Helper1);
            }
        });
    }
}

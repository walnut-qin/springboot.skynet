package com.kaos.skynet.mapper.inpatient.surgery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.kaos.skynet.enums.impl.common.ValidStateEnum;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryStatusEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsApplyTests {
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    @Test
    public void queryMetOpsApply() {
        this.metOpsApplyMapper.queryMetOpsApply(null);
        this.metOpsApplyMapper.queryMetOpsApply("554750");
    }

    @Test
    public void queryApplies() {
        Calendar now = Calendar.getInstance();
        Date endDate = now.getTime();
        now.add(Calendar.DATE, -1);
        Date beginDate = now.getTime();

        this.metOpsApplyMapper.queryApplies(beginDate, endDate, null, null);
        this.metOpsApplyMapper.queryApplies(beginDate, endDate, new ArrayList<>() {
            {
                add(SurgeryStatusEnum.手术安排);
            }
        }, null);
    }

    @Test
    public void queryPatientMetOpsApplies() {
        Calendar now = Calendar.getInstance();
        Date endDate = now.getTime();
        now.add(Calendar.DATE, -1);
        Date beginDate = now.getTime();

        this.metOpsApplyMapper.queryPatientMetOpsApplies(null, null, null, null);
        this.metOpsApplyMapper.queryPatientMetOpsApplies("0000703959", beginDate, endDate, ValidStateEnum.有效);
    }
}

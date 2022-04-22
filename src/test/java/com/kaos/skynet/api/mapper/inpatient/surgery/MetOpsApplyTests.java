package com.kaos.skynet.api.mapper.inpatient.surgery;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import com.kaos.skynet.enums.impl.common.ValidStateEnum;

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
        var ret = this.metOpsApplyMapper.queryApplies("1290", LocalDateTime.parse("2022-04-22T00:00:00"),
                LocalDateTime.parse("2022-04-22T23:59:59"), null, false, ValidStateEnum.有效);
        if (ret != null) {
            ret.clear();
        }
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

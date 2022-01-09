package com.kaos.his.inpatient.surgery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.SurgeryStatusEnum;
import com.kaos.his.mapper.inpatient.surgery.MetOpsApplyMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsApplyTests {
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    @Test
    public void queryMetOpsApply() {
        this.metOpsApplyMapper.queryMetOpsApply("554750", null, null);
        this.metOpsApplyMapper.queryMetOpsApply("554750", new ArrayList<>() {
            {
                add(SurgeryStatusEnum.手术安排);
            }
        }, null);
    }

    @Test
    public void queryMetOpsAppliesInDept() {
        Calendar now = Calendar.getInstance();
        Date endDate = now.getTime();
        now.add(Calendar.DATE, -1);
        Date beginDate = now.getTime();

        this.metOpsApplyMapper.queryMetOpsAppliesInDept("1000", beginDate, endDate, null, null);
        this.metOpsApplyMapper.queryMetOpsAppliesInDept("1000", beginDate, endDate, new ArrayList<>() {
            {
                add(SurgeryStatusEnum.手术安排);
            }
        }, null);
    }

    @Test
    public void queryMetOpsAppliesInDeptOwn() {
        Calendar now = Calendar.getInstance();
        Date endDate = now.getTime();
        now.add(Calendar.DATE, -1);
        Date beginDate = now.getTime();

        this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.All, beginDate, endDate, null, null);
        this.metOpsApplyMapper.queryMetOpsAppliesInDeptOwn(DeptOwnEnum.East, beginDate, endDate, new ArrayList<>() {
            {
                add(SurgeryStatusEnum.手术安排);
            }
        }, null);
    }
}

package com.kaos.skynet.api.mapper.inpatient.fee.balance.dayreport;

import java.util.Date;

import com.kaos.skynet.enums.impl.common.DeptOwnEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbDayReportTests {
    @Autowired
    FinIpbDayReportMapper finIpbDayReportMapper;

    @Test
    public void queryDayReport() {
        this.finIpbDayReportMapper.queryDayReport("163691");
    }

    @Test
    public void queryDayReports() {
        this.finIpbDayReportMapper.queryDayReprots(new Date(), new Date(), DeptOwnEnum.East);
        this.finIpbDayReportMapper.queryDayReprots(new Date(), new Date(), DeptOwnEnum.All);
        this.finIpbDayReportMapper.queryDayReprots(new Date(), new Date(), null);
    }
}

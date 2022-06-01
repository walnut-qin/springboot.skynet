package com.kaos.skynet.api.data.mapper.inpatient.fee.balance.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbDayReportDetailMapperTests {
    @Autowired
    FinIpbDayReportDetailMapper dayReportDetailMapper;

    @Test
    void queryDayReportDetail() {
        dayReportDetailMapper.queryDayReportDetail("347299", "冲销出院病人预收款");
    }

    @Test
    void queryDayReportDetails() {
        dayReportDetailMapper.queryDayReportDetails("347299");
    }
}

package com.kaos.his.inpatient.balance.dayreport;

import com.kaos.his.mapper.inpatient.balance.dayreport.FinIpbDayReportMapper;

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
}

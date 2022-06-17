package com.kaos.skynet.api.data.his.mapper.inpatient.fee.balance.report;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.his.enums.DeptOwnEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbDayReportMapperTests {
    @Autowired
    FinIpbDayReportMapper dayReportMapper;

    @Test
    void queryDayReport() {
        dayReportMapper.queryDayReport("347299");
    }

    @Test
    void queryDayReports() {
        dayReportMapper.queryDayReprots(FinIpbDayReportMapper.Key.builder()
                .deptOwn(DeptOwnEnum.All)
                .beginRptDate(LocalDateTime.now().minusMinutes(1))
                .build());
    }
}

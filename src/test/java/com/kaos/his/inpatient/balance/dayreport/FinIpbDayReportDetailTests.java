package com.kaos.his.inpatient.balance.dayreport;

import com.kaos.his.entity.inpatient.balance.dayreport.FinIpbDayReportDetail;
import com.kaos.his.mapper.inpatient.balance.dayreport.FinIpbDayReportDetailMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbDayReportDetailTests {
    @Autowired
    FinIpbDayReportDetailMapper finIpbDayReportDetailMapper;

    @Test
    public void queryDayReportDetail() {
        this.finIpbDayReportDetailMapper.queryDayReportDetail("163691", "住院病人预收款工行POS");
    }

    @Test
    public void queryDayReportDetails() {
        this.finIpbDayReportDetailMapper.queryDayReportDetails("163691");
    }

    @Test
    public void updateDayReportDetail() {
        this.finIpbDayReportDetailMapper.insertDayReportDetail(new FinIpbDayReportDetail() {
            {
                statNo = "999999";
                statCode = "test";
                totCost = 0.01;
            }
        });
    }
}

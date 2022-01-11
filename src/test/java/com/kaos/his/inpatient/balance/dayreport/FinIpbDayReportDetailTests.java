package com.kaos.his.inpatient.balance.dayreport;

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
        var detail = this.finIpbDayReportDetailMapper.queryDayReportDetail("999999", "test");
        if (detail != null) {
            var cnt = this.finIpbDayReportDetailMapper.updateDayReportDetail(detail.statNo, detail.statCode,
                    detail.totCost);
            if (cnt != 1) {
                throw new RuntimeException("更新测试失败");
            }
        }
    }
}

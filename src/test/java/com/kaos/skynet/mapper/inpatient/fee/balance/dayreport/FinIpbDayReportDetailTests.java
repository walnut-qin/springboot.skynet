package com.kaos.skynet.mapper.inpatient.fee.balance.dayreport;

import com.kaos.skynet.entity.inpatient.fee.balance.dayreport.FinIpbDayReportDetail;

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
    public void insertOrUpdateDayReportDetail() {
        var detail = this.finIpbDayReportDetailMapper.queryDayReportDetail("000000", "测试数据");
        if (detail == null) {
            var cnt = this.finIpbDayReportDetailMapper.insertDayReportDetail(new FinIpbDayReportDetail() {
                {
                    statNo = "000000";
                    statCode = "测试数据";
                    totCost = 0.01;
                }
            });
            if (cnt != 1) {
                throw new RuntimeException();
            }
        } else {
            var cnt = this.finIpbDayReportDetailMapper.updateDayReportDetail("000000", "测试数据", 0.01);
            if (cnt != 1) {
                throw new RuntimeException();
            }
        }
    }
}

package com.kaos.skynet.api.mapper.outpatient.fee;

import java.time.LocalDateTime;
import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOpbFeeDetailMapperTests {
    @Autowired
    FinOpbFeeDetailMapper finOpbFeeDetailMapper;

    @Test
    public void queryFeeDetailsWithClinicCode() {
        Calendar beginDate = Calendar.getInstance();
        beginDate.set(Calendar.YEAR, 2013);
        beginDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
        beginDate.set(Calendar.DATE, 1);
        beginDate.set(Calendar.HOUR_OF_DAY, 0);
        beginDate.set(Calendar.MINUTE, 0);
        beginDate.set(Calendar.SECOND, 0);
        Calendar endDate = Calendar.getInstance();
        endDate.set(Calendar.YEAR, 2013);
        endDate.set(Calendar.MONTH, Calendar.SEPTEMBER);
        endDate.set(Calendar.DATE, 1);
        endDate.set(Calendar.HOUR_OF_DAY, 23);
        endDate.set(Calendar.MINUTE, 59);
        endDate.set(Calendar.SECOND, 59);
        this.finOpbFeeDetailMapper.queryFeeDetailsWithClinicCode("160670", "F00000005341", beginDate.getTime(),
                endDate.getTime());
    }

    @Test
    public void queryFeeDetailsWithCardNo() {
        this.finOpbFeeDetailMapper.queryFeeDetailsWithCardNo("cardNo", "itemCode", LocalDateTime.now(), LocalDateTime.now());
    }
}
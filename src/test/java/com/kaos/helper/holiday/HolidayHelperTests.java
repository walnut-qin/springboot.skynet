package com.kaos.helper.holiday;

import java.util.Date;

import com.kaos.helper.holiday.impl.HolidayHelperImpl;
import com.kaos.his.HisApplication;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HisApplication.class)
public class HolidayHelperTests {
    HolidayHelper holidayHelper = new HolidayHelperImpl();

    @Test
    public void Test() {
        this.holidayHelper.getDayInfo(new Date());
    }
}

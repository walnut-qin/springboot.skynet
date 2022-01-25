package com.kaos.helper.holiday;

import java.util.Date;

import com.kaos.helper.holiday.impl.HolidayManagerImpl;
import com.kaos.his.HisApplication;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = HisApplication.class)
public class HolidayTests {
    HolidayManager holidayManager = new HolidayManagerImpl();

    Logger logger = Logger.getLogger(HolidayTests.class.getName());

    @Test
    public void Test() {
        var v = this.holidayManager.getDayInfo(new Date());

        this.logger.info(String.format("code = %s", v.type.week.getDescription()));
    }
}

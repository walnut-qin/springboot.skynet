package com.kaos.skynet.api.mapper.inpatient.fee;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbItemListMapperTests {
    /**
     * 数据库接口
     */
    @Autowired
    FinIpbItemListMapper itemListMapper;

    @Test
    public void queryItemLists() {
        Calendar begin = Calendar.getInstance();
        begin.set(Calendar.YEAR, 2022);
        begin.set(Calendar.MONTH, Calendar.FEBRUARY);
        begin.set(Calendar.DATE, 22);
        begin.set(Calendar.HOUR_OF_DAY, 00);
        begin.set(Calendar.MINUTE, 00);
        begin.set(Calendar.SECOND, 00);
        Calendar end = (Calendar) begin.clone();
        end.set(Calendar.HOUR_OF_DAY, 00);
        end.set(Calendar.MINUTE, 00);
        end.set(Calendar.SECOND, 59);
        this.itemListMapper.queryItemLists(null, begin.getTime(), end.getTime());
    }
}

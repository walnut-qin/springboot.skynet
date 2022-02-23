package com.kaos.his.mapper.inpatient.fee;

import java.util.Date;

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
    public void queryFeeInfos() {
        this.itemListMapper.queryItemLists(null, new Date(), new Date());
    }
}

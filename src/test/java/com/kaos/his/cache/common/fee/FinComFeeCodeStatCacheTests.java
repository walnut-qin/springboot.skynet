package com.kaos.his.cache.common.fee;

import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.fee.FinComFeeCodeStat;
import com.kaos.his.enums.impl.common.MinFeeEnum;
import com.kaos.his.enums.impl.common.ReportTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComFeeCodeStatCacheTests {
    @Autowired
    Cache<ReportTypeEnum, Cache<MinFeeEnum, FinComFeeCodeStat>> cache;

    @Test
    public void getCacheValue() {
        this.cache.getValue(ReportTypeEnum.门诊发票).getValue(MinFeeEnum.B超);
    }
}

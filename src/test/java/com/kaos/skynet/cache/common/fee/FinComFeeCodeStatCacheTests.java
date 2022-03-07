package com.kaos.skynet.cache.common.fee;

import com.kaos.skynet.cache.Cache;
import com.kaos.skynet.entity.common.fee.FinComFeeCodeStat;
import com.kaos.skynet.enums.impl.common.MinFeeEnum;
import com.kaos.skynet.enums.impl.common.ReportTypeEnum;

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

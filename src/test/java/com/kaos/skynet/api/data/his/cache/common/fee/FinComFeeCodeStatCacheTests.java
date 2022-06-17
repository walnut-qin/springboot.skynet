package com.kaos.skynet.api.data.his.cache.common.fee;

import com.kaos.skynet.api.data.his.entity.common.fee.FinComFeeCodeStat.ReportTypeEnum;
import com.kaos.skynet.api.data.his.enums.MinFeeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComFeeCodeStatCacheTests {
    @Autowired
    FinComFeeCodeStatCache feeCodeStatCache;

    @Test
    public void getCacheValue() {
        feeCodeStatCache.get(new FinComFeeCodeStatCache.Key(ReportTypeEnum.门诊发票, MinFeeEnum.B超));
    }
}

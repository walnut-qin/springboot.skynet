package com.kaos.skynet.api.mapper.common.fee;

import com.kaos.skynet.enums.impl.common.MinFeeEnum;
import com.kaos.skynet.enums.impl.common.ReportTypeEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinComFeeCodeStatMapperTests {
    @Autowired
    FinComFeeCodeStatMapper feeCodeStatMapper;

    @Test
    public void queryFeeCodeStat() throws Exception {
        this.feeCodeStatMapper.queryFeeCodeStat(null, null);
        this.feeCodeStatMapper.queryFeeCodeStat(ReportTypeEnum.门诊发票, MinFeeEnum.B超);
    }
}

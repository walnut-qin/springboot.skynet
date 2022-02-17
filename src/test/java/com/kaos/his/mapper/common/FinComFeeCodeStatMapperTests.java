package com.kaos.his.mapper.common;

import com.kaos.his.enums.common.FeeStatTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;

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
        this.feeCodeStatMapper.queryFeeCodeStat(FeeStatTypeEnum.门诊发票, MinFeeEnum.B超);
    }
}

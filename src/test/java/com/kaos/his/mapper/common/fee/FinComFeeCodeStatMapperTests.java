package com.kaos.his.mapper.common.fee;

import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.his.mapper.common.FinComFeeCodeStatMapper;
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
        this.feeCodeStatMapper.queryFeeCodeStat(ReportTypeEnum.门诊发票, MinFeeEnum.B超);
    }
}

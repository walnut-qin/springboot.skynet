package com.kaos.his.mapper.outpatient.fee;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOpbFeeDetailMapperTests {
    @Autowired
    FinOpbFeeDetailMapper finOpbFeeDetailMapper;

    @Test
    public void queryFeeDetailsWithClinicCode() {
        this.finOpbFeeDetailMapper.queryFeeDetailsWithClinicCode("clinicCode", "itemCode", new Date(), new Date());
    }

    @Test
    public void queryFeeDetailsWithCardNo() {
        this.finOpbFeeDetailMapper.queryFeeDetailsWithCardNo("cardNo", "itemCode", new Date(), new Date());
    }
}

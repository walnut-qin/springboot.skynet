package com.kaos.his.mapper.inpatient.fee;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbMedicineListMapperTests {
    @Autowired
    FinIpbMedicineListMapper medicineListMapper;

    @Test
    public void queryFeeInfos() {
        this.medicineListMapper.queryMedicineLists(null, new Date(), new Date());
    }
}

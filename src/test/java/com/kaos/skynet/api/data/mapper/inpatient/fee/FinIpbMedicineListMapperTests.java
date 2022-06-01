package com.kaos.skynet.api.data.mapper.inpatient.fee;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinIpbMedicineListMapperTests {
    @Autowired
    FinIpbMedicineListMapper medicineListMapper;

    @Test
    void queryMedicineLists() {
        medicineListMapper.queryMedicineLists(FinIpbMedicineListMapper.Key.builder()
                .deptCode("1000")
                .beginFeeDate(LocalDateTime.now().minusDays(1))
                .build());
    }
}

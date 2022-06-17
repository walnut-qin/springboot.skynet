package com.kaos.skynet.api.data.his.mapper.inpatient.surgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsItemMapperTests {
    @Autowired
    MetOpsOperationItemMapper metOpsItemMapper;

    @Test
    void queryMetOpsItem() {
        metOpsItemMapper.queryMetOpsItem("operationNo", "S991");
    }

    @Test
    void queryMetOpsItems() {
        metOpsItemMapper.queryMetOpsItems("operationNo");
    }
}

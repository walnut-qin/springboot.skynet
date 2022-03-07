package com.kaos.skynet.mapper.inpatient.surgery;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsItemTests {
    @Autowired
    MetOpsItemMapper metOpsItemMapper;

    @Test
    public void queryMetOpsItem() {
        this.metOpsItemMapper.queryMetOpsItem("685", "S991");
    }

    @Test
    public void queryMetOpsItems() {
        this.metOpsItemMapper.queryMetOpsItems("685");
    }
}

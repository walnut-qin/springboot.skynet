package com.kaos.skynet.api.data.his.mapper.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MetOpsnWyDocMapperTests {
    @Autowired
    MetOpsnWyDocMapper metOpsnWyDocMapper;

    @Test
    void queryEmpl() {
        metOpsnWyDocMapper.queryEmpl(null);
        metOpsnWyDocMapper.queryEmpl("W00014");
    }
}

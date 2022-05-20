package com.kaos.skynet.api.data.mapper.outpatient.fee;

import java.time.LocalDateTime;

import com.kaos.skynet.api.data.mapper.common.SequenceMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinOpbFeeDetailMapperTests {
    @Autowired
    FinOpbFeeDetailMapper finOpbFeeDetailMapper;

    @Autowired
    SequenceMapper sequenceMapper;

    @Test
    public void queryFinOpbFeeDetails() {
        finOpbFeeDetailMapper.queryFeeDetails(new FinOpbFeeDetailMapper.Key() {
            {
                setClinicCode("160670");
                setItemCode("F00000005341");
                setOperBeginDate(LocalDateTime.of(2013, 9, 1, 0, 0, 0));
                setOperEndDate(LocalDateTime.of(2013, 9, 1, 23, 59, 59));
            }
        });
    }
}
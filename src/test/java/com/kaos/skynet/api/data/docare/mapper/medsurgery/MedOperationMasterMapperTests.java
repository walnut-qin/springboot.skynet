package com.kaos.skynet.api.data.docare.mapper.medsurgery;

// import java.time.LocalDateTime;

// import com.google.common.collect.Lists;
// import com.kaos.skynet.api.data.docare.entity.medsurgery.MedOperationMaster.OperStatusEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MedOperationMasterMapperTests {
    @Autowired
    MedOperationMasterMapper medOperationMasterMapper;

    @Test
    public void queryOperationMaster() {
        // medOperationMasterMapper.queryOperationMaster("0001713762", 1, 1);
    }

    @Test
    public void queryOperationMasters() {
        // var keyBuilder = MedOperationMasterMapper.Key.builder();
        // keyBuilder.beginInDateTime(LocalDateTime.now().minusDays(1));
        // keyBuilder.operStatus(Lists.newArrayList(
        //         OperStatusEnum.出手术室,
        //         OperStatusEnum.准备复苏,
        //         OperStatusEnum.入复苏室,
        //         OperStatusEnum.出复苏室,
        //         OperStatusEnum.转入病房));
        // medOperationMasterMapper.queryOperationMasters(keyBuilder.build());
    }
}

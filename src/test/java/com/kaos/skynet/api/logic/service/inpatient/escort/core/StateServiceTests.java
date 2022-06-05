package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.core.type.utils.IntegerUtils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j;

@Log4j
@SpringBootTest
public class StateServiceTests {
    @Autowired
    StateService stateService;

    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    @Test
    void queryEscortState() {
        stateService.queryEscortState("0000030651");
    }

    @Test
    void test() {
        var iterable = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .states(Lists.newArrayList(
                        EscortStateRec.StateEnum.无核酸检测结果,
                        EscortStateRec.StateEnum.等待院内核酸检测结果,
                        EscortStateRec.StateEnum.等待院外核酸检测结果审核,
                        EscortStateRec.StateEnum.生效中,
                        EscortStateRec.StateEnum.其他))
                .build());
        for (var escortInfo : iterable) {
            // 检索数据库状态
            var states = escortStateRecMapper.queryEscortStateRecs(escortInfo.getEscortNo());
            states.sort((x, y) -> {
                return IntegerUtils.compare(y.getRecNo(), x.getRecNo());
            });
            var realState = states.get(0);

            // 判断逻辑状态
            var logicState = stateService.queryEscortState(escortInfo.getEscortNo());

            if (realState.getState() != logicState.getState()) {
                log.info(String.format("状态不一致，陪护证号 = %s, 数据库记载 = %s, 逻辑 = %s, 原因 = %s", escortInfo.getEscortNo(),
                        realState.getState().getDescription(), logicState.getState().getDescription(), logicState.getReason()));
            }
        }
    }
}

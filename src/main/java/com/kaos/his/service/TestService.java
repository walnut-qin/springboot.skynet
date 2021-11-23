package com.kaos.his.service;

import java.util.Date;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.mapper.credential.EscortCardMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    EscortCardMapper escortCardMapper;

    @Transactional
    public EscortCard Run(String param) {
        var escort = this.escortCardMapper.QueryEscort(param);

        // 取最后一个状态
        var lstState = escort.states.get(escort.states.size() - 1);

        // 定义一个新状态
        var newState = new EscortCard.EscortState();
        newState.escortNo = lstState.escortNo;
        newState.recNo = lstState.recNo + 1;
        newState.state = EscortStateEnum.注销;
        newState.operDate = new Date();

        // 插入状态
        this.escortCardMapper.UpdateEscortState(newState);
        escort.states.add(newState);

        return escort;
    }
}

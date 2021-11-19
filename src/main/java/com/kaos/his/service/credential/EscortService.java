package com.kaos.his.service.credential;

import java.util.List;

import com.kaos.his.entity.credential.Escort;
import com.kaos.his.mapper.credential.EscortMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EscortService {
    @Autowired
    EscortMapper escortMapper;

    public Escort GetEscortByEscortNo(String escortNo) {
        return this.escortMapper.GetEscort(escortNo);
    }

    public List<Escort> GetEscortsByHelperCardNo(String cardNo) {
        return this.escortMapper.GetEscortsByHelperCardNo(cardNo);
    }
}

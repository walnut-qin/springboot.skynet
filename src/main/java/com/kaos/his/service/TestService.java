package com.kaos.his.service;

import com.kaos.his.entity.surgery.MetOpsApply;
import com.kaos.his.mapper.surgery.MetOpsApplyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    @Transactional
    public MetOpsApply Run(String param) {
        // 查询出指定的药品实体
        return this.metOpsApplyMapper.queryMetOpsApply(param);
    }
}

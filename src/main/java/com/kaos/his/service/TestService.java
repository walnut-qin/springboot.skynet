package com.kaos.his.service;

import com.kaos.his.entity.product.Order;
import com.kaos.his.mapper.lis.NucleicAcidTestMapper;
import com.kaos.his.mapper.product.OrderMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    OrderMapper inpatientMapper;

    @Autowired
    NucleicAcidTestMapper nucleicAcidTestMapper;

    public Order Run(String param) {
        return this.inpatientMapper.QueryOrder(param);
    }
}

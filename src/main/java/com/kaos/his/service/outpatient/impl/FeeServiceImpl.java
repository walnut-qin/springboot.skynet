package com.kaos.his.service.outpatient.impl;

import java.util.List;

import com.kaos.his.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.his.service.outpatient.FeeService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeeServiceImpl implements FeeService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(GcpServiceImpl.class.getName());

    /**
     * 配置列表接口
     */
    @Autowired
    FinOpbFeeDetailMapper feeDetailMapper;

    @Override
    public void undrugPriced(String clinicCode, String operCode, List<String> itemCodes, String termNo) {
        
    }
}

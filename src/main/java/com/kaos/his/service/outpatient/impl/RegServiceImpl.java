package com.kaos.his.service.outpatient.impl;

import java.util.Date;

import com.kaos.his.service.outpatient.RegService;

import org.apache.log4j.Logger;

public class RegServiceImpl implements RegService {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(RegServiceImpl.class);

    @Override
    public void freeRegister(String cardNo, String idenNo, String deptCode, String docCode, Date seeDate) {

    }
}

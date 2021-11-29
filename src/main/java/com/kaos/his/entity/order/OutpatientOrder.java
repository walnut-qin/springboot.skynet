package com.kaos.his.entity.order;

import java.util.Date;

public class OutpatientOrder {
    /**
     * 医嘱流水号
     */
    public String moOrder = null;

    /**
     * 开立医嘱时间
     */
    public Date moDate = null;

    /**
     * 索引号，门诊为卡号，住院为住院号
     */
    public String indexNo = null;

    /**
     * 项目编码
     */
    public String termId = null;

    /**
     * 项目名称
     */
    public String termName = null;
}

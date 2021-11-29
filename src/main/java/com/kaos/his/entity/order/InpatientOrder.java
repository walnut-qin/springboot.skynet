package com.kaos.his.entity.order;

import java.util.Date;

public class InpatientOrder {
    /**
     * 医嘱流水号
     */
    public String moOrder = null;

    /**
     * 开立医嘱时间
     */
    public Date moDate = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 项目编码
     */
    public String termId = null;

    /**
     * 项目名称
     */
    public String termName = null;
}

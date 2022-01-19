package com.kaos.his.controller.inpatient.escort.entity;

import java.util.Date;
import java.util.List;

public class QueryAnnexInDeptRspBody {
    /**
     * 附件编码
     */
    public String annexNo = null;

    /**
     * 陪护人姓名
     */
    public String helperName = null;

    /**
     * 附件链接
     */
    public String picUrl = null;

    /**
     * 被陪护患者列表
     */
    public List<String> patientNames = null;

    /**
     * 审核结果
     */
    public Boolean negative = null;

    /**
     * 检验日期
     */
    public Date inspectDate = null;
}

package com.kaos.his.entity.credential;

import java.util.Date;

/**
 * 陪护人附件
 */
public class EscortAnnex {
    /**
     * 附件编号，主键
     */
    public String annexNo = null;

    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 附件上传日期
     */
    public Date uploadDate = null;

    /**
     * 附件外链
     */
    public String picUrl = null;

    /**
     * 审核结果： null - 未审核； 0 - 核酸阳性； 1 - 核酸阴性
     */
    public Boolean cfmResult = null;

    /**
     * 审核日期
     */
    public Date cfmDate = null;

    /**
     * 审核结果 - 核酸结果生效时间
     */
    public Date cfmNatDate = null;
}

package com.kaos.his.entity.credential;

import java.util.Date;

/**
 * 核酸检测（附件）审核信息
 */
public class AnnexCheckInfo {
    /**
     * 附件编码
     */
    public String annexNo = null;

    /**
     * 审核时间
     */
    public Date operDate = null;

    /**
     * 审核结果
     */
    public Boolean negative = null;

    /**
     * 核酸检测时间
     */
    public Date execDate = null;
}

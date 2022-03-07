package com.kaos.skynet.api.service.inf.inpatient.escort;

import java.util.Date;
import java.util.List;

import com.kaos.skynet.entity.inpatient.escort.EscortAnnexChk;
import com.kaos.skynet.entity.inpatient.escort.EscortAnnexInfo;

public interface AnnexService {
    /**
     * 上传附件
     * 
     * @param helperCardNo
     * @param url
     * @return
     */
    EscortAnnexInfo uploadAnnex(String helperCardNo, String url);

    /**
     * 审核附件
     * 
     * @param annexNo
     * @param negativeFlag
     * @param inspectDate
     * @return
     */
    EscortAnnexChk checkAnnex(String annexNo, String checker, Boolean negativeFlag, Date inspectDate);

    /**
     * 查询指定科室的附件
     * 
     * @param deptCode
     * @param checked
     * @return
     */
    List<EscortAnnexInfo> queryAnnexInfoInDept(String deptCode, Boolean checked);
}

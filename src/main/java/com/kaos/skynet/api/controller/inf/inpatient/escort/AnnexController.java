package com.kaos.skynet.api.controller.inf.inpatient.escort;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

public interface AnnexController {
    /**
     * 上传陪护附件
     * 
     * @param helperCardNo
     * @param url
     * @return
     */
    String uploadAnnex(@NotNull(message = "陪护人卡号不能为空") String helperCardNo,
            @NotNull(message = "附件链接不能为空") String url);

    /**
     * 审核附件
     * 
     * @param annexNo
     * @param checker
     * @param negativeFlag
     * @param inspectDate
     */
    void checkAnnex(@NotNull(message = "附件号不能为空") String annexNo,
            @NotNull(message = "审核人不能为空") String checker,
            @NotNull(message = "审核结果不能为空") Boolean negativeFlag,
            @NotNull(message = "检验时间不能为空") LocalDateTime inspectDate);

    /**
     * 查询指定科室的附件信息
     * 
     * @param deptCode
     * @param checked
     * @return
     */
    List<QueryAnnexInDeptRsp> queryAnnexInDept(@NotNull(message = "科室编码不能为空") String deptCode,
            @NotNull(message = "审核标识不能为空") Boolean checked);

    /**
     * 查询指定科室的附件信息body
     */
    public static class QueryAnnexInDeptRsp {
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
        public LocalDateTime inspectDate = null;
    }
}

package com.kaos.skynet.api.controller.inf.inpatient.fee.balance.report;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.enums.DeptOwnEnum;

public interface DayReportController {
    /**
     * 查询新医保统筹
     * 
     * @param balancer
     * @param beginDate
     * @param endDate
     * @return
     */
    Double queryNewYbPubCost(@NotNull(message = "结算员不能为空") String balancer,
            @NotNull(message = "开始时间不能为空") Date beginDate,
            @NotNull(message = "结束时间不能为空") Date endDate);

    /**
     * 查询新医保账户
     * 
     * @param balancer
     * @param beginDate
     * @param endDate
     * @return
     */
    Double queryNewYbPayCost(@NotNull(message = "结算员不能为空") String balancer,
            @NotNull(message = "开始时间不能为空") Date beginDate,
            @NotNull(message = "结束时间不能为空") Date endDate);

    /**
     * 修正指定日结记录
     * 
     * @param statNo
     * @return
     */
    String fixNewYbData(@NotNull(message = "日结编码不能为空") String statNo);

    /**
     * 批量修改日结记录
     * 
     * @param req
     * @return
     */
    String fixNewYbDataInDeptOwn(@Valid FixInDeptOwnReq req);

    public static class FixInDeptOwnReq {
        /**
         * 开始时间
         */
        @NotNull(message = "开始时间不能为空")
        public Date beginDate = null;

        /**
         * 结束时间
         */
        @NotNull(message = "结束时间不能为空")
        public Date endDate = null;

        /**
         * 院区不能为空
         */
        @NotNull(message = "院区不能为空")
        public DeptOwnEnum deptOwn = null;
    }
}

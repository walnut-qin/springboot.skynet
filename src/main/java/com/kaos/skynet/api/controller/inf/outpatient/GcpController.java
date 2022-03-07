package com.kaos.skynet.api.controller.inf.outpatient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Validated
public interface GcpController {
    /**
     * 判断是否拥有GCP权限
     * 
     * @param req 请求body
     * @return
     */
    Boolean test(@NotNull(message = "body不能为空") TestReq req);

    /**
     * 请求body
     */
    @Validated
    public static class TestReq {
        /**
         * 科室编码
         */
        @NotBlank(message = "科室编码不能为空")
        public String deptCode = null;

        /**
         * 门诊号
         */
        @NotBlank(message = "门诊号不能为空")
        public String clinicCode = null;
    }
}

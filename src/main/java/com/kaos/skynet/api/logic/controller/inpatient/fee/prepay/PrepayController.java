package com.kaos.skynet.api.logic.controller.inpatient.fee.prepay;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.logic.service.inpatient.fee.prepay.PrepayService;
import com.kaos.skynet.core.spring.net.RspWrapper;
import com.kaos.skynet.core.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.spring.net.MediaType;
import com.kaos.skynet.core.type.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@Validated
@RestController
@RequestMapping("/api/inpatient/fee/prepay")
class PrepayController {
    /**
     * 预交金业务
     */
    @Autowired
    PrepayService prepayService;

    /**
     * 隔日召回修改预交金
     * 
     * @param reqBody
     * @return
     */
    @ApiName("隔日召回修改预交金")
    @RequestMapping(value = "fixRecallPrepay", method = RequestMethod.POST, produces = MediaType.JSON)
    RspWrapper<Object> fixRecallPrepay(@RequestBody @Valid FixRecallPrepay.ReqBody reqBody) {
        try {
            // 启动事务处理
            var prepayModifyResults = prepayService.fixRecallPrepay(StringUtils.leftPad(reqBody.patientNo, 10, '0'));

            // 构造响应
            var builder = FixRecallPrepay.RspBody.builder();
            return RspWrapper.wrapSuccessResponse(prepayModifyResults.stream().map(x -> {
                builder.inPatientNo(x.getInPatientNo());
                builder.happenNo(x.getHappenNo());
                builder.oldCost(x.getOldCost());
                builder.newCost(x.getNewCost());
                return builder.build();
            }).toList());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    /**
     * fixRecallPrepay的请求和响应body
     */
    static class FixRecallPrepay {
        /**
         * 请求body
         */
        static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "住院号不能为空")
            String patientNo;
        }

        /**
         * 响应body
         */
        @Builder
        static class RspBody {
            /**
             * 住院流水号
             */
            String inPatientNo;

            /**
             * 预交金序号
             */
            Integer happenNo;

            /**
             * 原值
             */
            Double oldCost;

            /**
             * 新值
             */
            Double newCost;
        }
    }
}

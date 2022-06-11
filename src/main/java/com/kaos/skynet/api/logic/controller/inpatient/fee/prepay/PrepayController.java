package com.kaos.skynet.api.logic.controller.inpatient.fee.prepay;

import java.util.List;

import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.api.logic.service.inpatient.fee.prepay.PrepayService;
import com.kaos.skynet.core.json.Json;
import com.kaos.skynet.core.type.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/api/inpatient/fee/prepay")
public class PrepayController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

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
    @RequestMapping(value = "fixRecallPrepay", method = RequestMethod.GET, produces = MediaType.JSON)
    public FixRecallPrepay.RspBody fixRecallPrepay(@RequestBody FixRecallPrepay.ReqBody reqBody) {
        // 记录日志
        log.info(String.format("隔日召回修改预交金, reqBody = %s", json.toJson(reqBody)));

        // 启动事务处理
        var prepayModifyResults = prepayService.fixRecallPrepay(StringUtils.leftPad(reqBody.getPatientNo(), 10, '0'));

        // 构造响应
        var builder = FixRecallPrepay.RspBody.builder();
        builder.size(prepayModifyResults.size());
        builder.data(prepayModifyResults.stream().map(x -> {
            var itemBuilder = FixRecallPrepay.RspBody.Item.builder();
            itemBuilder.inPatientNo(x.getInPatientNo());
            itemBuilder.happenNo(x.getHappenNo());
            itemBuilder.oldCost(x.getOldCost());
            itemBuilder.newCost(x.getNewCost());
            return itemBuilder.build();
        }).toList());
        return builder.build();
    }

    /**
     * fixRecallPrepay的请求和响应body
     */
    public static class FixRecallPrepay {
        /**
         * 请求body
         */
        @Getter
        public static class ReqBody {
            /**
             * 住院号
             */
            @NotBlank(message = "住院号不能为空")
            private String patientNo;
        }

        /**
         * 响应body
         */
        @Getter
        @Builder
        public static class RspBody {
            /**
             * 修改的数量
             */
            private Integer size;

            /**
             * 修改明细
             */
            private List<Item> data;

            /**
             * 明细内容
             */
            @Getter
            @Builder
            public static class Item {
                /**
                 * 住院流水号
                 */
                private String inPatientNo;

                /**
                 * 预交金序号
                 */
                private Integer happenNo;

                /**
                 * 原值
                 */
                private Double oldCost;

                /**
                 * 新值
                 */
                private Double newCost;
            }
        }
    }
}

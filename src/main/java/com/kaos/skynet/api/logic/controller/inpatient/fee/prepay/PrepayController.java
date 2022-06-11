package com.kaos.skynet.api.logic.controller.inpatient.fee.prepay;

import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

        return null;
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
        public static class RspBody {

        }
    }
}

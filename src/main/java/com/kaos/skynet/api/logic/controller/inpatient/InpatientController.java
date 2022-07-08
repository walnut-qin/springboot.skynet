package com.kaos.skynet.api.logic.controller.inpatient;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.data.his.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@CrossOrigin
@Validated
@RestController
@RequestMapping("/api/inpatient/surgery")
public class InpatientController {
    /**
     * 实体信息服务
     */
    @Autowired
    FinIprInMainInfoCache inMainInfoCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @ApiName("获取住院患者信息")
    @RequestMapping(value = "queryInpatientInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    QueryInpatientInfo.RspBody queryInpatientInfo(@RequestBody @Valid QueryInpatientInfo.ReqBody reqBody) {
        // 调用服务
        var patient = this.inMainInfoCache.get("ZY01".concat(reqBody.patientNo));
        if (patient == null) {
            throw new RuntimeException("住院号不存在!");
        }

        // 构造响应体
        var rspBuilder = QueryInpatientInfo.RspBody.builder();
        rspBuilder.patientNo(patient.getPatientNo());
        rspBuilder.cardNo(patient.getCardNo());
        rspBuilder.deptCode(patient.getDeptCode());
        rspBuilder.houseDocCode(patient.getHouseDocCode());
        rspBuilder.chargeDocCode(patient.getChargeDocCode());
        rspBuilder.chiefDocCode(patient.getChiefDocCode());

        return rspBuilder.build();
    }

    static class QueryInpatientInfo {
        static class ReqBody {
            /**
             * 患者卡号
             */
            @NotBlank(message = "住院号不能为空")
            String patientNo;
        }

        @Builder
        static class RspBody {
            /**
             * 住院号
             */
            String patientNo;

            /**
             * 就诊卡号
             */
            String cardNo;

            /**
             * 住院科室编码
             */
            String deptCode;

            /**
             * 住院医师编码
             */
            String houseDocCode;

            /**
             * 主治医师编码
             */
            String chargeDocCode;

            /**
             * 主任医师编码
             */
            String chiefDocCode;
        }
    }
}

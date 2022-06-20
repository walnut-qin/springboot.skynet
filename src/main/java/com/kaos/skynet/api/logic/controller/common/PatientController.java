package com.kaos.skynet.api.logic.controller.common;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.Valid;

import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.http.RspWrapper;
import com.kaos.skynet.core.json.Json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("api/common/patient")
public class PatientController {
    /**
     * 序列化工具
     */
    @Autowired
    Json json;

    /**
     * 实体信息服务
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 查询患者信息
     * 
     * @param cardNo
     * @return
     */
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.POST, produces = MediaType.JSON)
    RspWrapper<QueryPatientInfo.RspBody> queryPatientInfo(@RequestBody @Valid QueryPatientInfo.ReqBody reqBody) {
        try {
            // 记录日志
            log.info("查询患者信息".concat(json.toJson(reqBody)));

            // 调用服务
            var patient = this.patientInfoCache.get(reqBody.cardNo);
            if (patient == null) {
                throw new RuntimeException("就诊卡不存在!");
            }

            // 构造响应体
            var rspBuilder = QueryPatientInfo.RspBody.builder();
            rspBuilder.cardNo(patient.getCardNo());
            rspBuilder.name(patient.getName());
            rspBuilder.sex(patient.getSex());
            rspBuilder.age(Period.between(patient.getBirthday().toLocalDate(), LocalDate.now()));
            rspBuilder.idenNo(patient.getIdentityCardNo());
            rspBuilder.tel(patient.getHomeTel());

            return RspWrapper.wrapSuccessResponse(rspBuilder.build());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    static class QueryPatientInfo {
        static class ReqBody {
            /**
             * 患者卡号
             */
            String cardNo;
        }

        @Builder
        static class RspBody {
            /**
             * 就诊卡号
             */
            String cardNo;

            /**
             * 患者姓名
             */
            String name;

            /**
             * 性别
             */
            SexEnum sex;

            /**
             * 年龄
             */
            Period age;

            /**
             * 身份证号
             */
            String idenNo;

            /**
             * 电话号码
             */
            String tel;
        }
    }
}

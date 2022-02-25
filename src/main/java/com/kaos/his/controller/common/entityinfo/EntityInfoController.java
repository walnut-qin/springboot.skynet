package com.kaos.his.controller.common.entityinfo;

import javax.validation.constraints.NotBlank;

import com.kaos.helper.type.TypeHelper;
import com.kaos.helper.type.impl.TypeHelperImpl;
import com.kaos.his.controller.common.entityinfo.entity.QueryPatientInfoRspBody;
import com.kaos.his.service.inf.common.PatientInfoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/common/entityinfo")
public class EntityInfoController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(EntityInfoController.class.getName());

    /**
     * 基本类型助手
     */
    TypeHelper typeHelper = new TypeHelperImpl();

    /**
     * 实体信息服务
     */
    @Autowired
    PatientInfoService entityInfoService;

    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public QueryPatientInfoRspBody queryPatientInfo(@NotBlank(message = "就诊卡号不能为空") String cardNo) {
        // 记录日志
        this.logger.info(String.format("查询患者信息(cardNo = %s)", cardNo));

        // 调用服务
        var patient = this.entityInfoService.queryPatientInfo(cardNo);

        // 构造响应体
        var rspBody = new QueryPatientInfoRspBody();
        rspBody.cardNo = patient.cardNo;
        rspBody.name = patient.name;
        rspBody.sex = patient.sex;
        rspBody.age = this.typeHelper.getAge(patient.birthday).toString();
        rspBody.identityCardNo = patient.identityCardNo;
        rspBody.tel = patient.homeTel;

        return rspBody;
    }
}

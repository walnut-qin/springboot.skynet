package com.kaos.his.controller.common.entityinfo;

import javax.validation.constraints.NotBlank;

import com.kaos.his.controller.common.entityinfo.entity.QueryPatientInfoRspBody;
import com.kaos.his.service.EntityInfoService;
import com.kaos.util.DateHelper;
import com.kaos.util.GsonHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * 实体信息服务
     */
    @Autowired
    EntityInfoService entityInfoService;

    @ResponseBody
    @RequestMapping(value = "queryPatientInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryPatientInfo(@NotBlank(message = "就诊卡号不能为空") String cardNo) {
        // 记录日志
        this.logger.info(String.format("查询患者信息(cardNo = %s)", cardNo));

        // 调用服务
        var patient = this.entityInfoService.queryPatient(cardNo);

        // 构造响应体
        var rspBody = new QueryPatientInfoRspBody();
        rspBody.cardNo = patient.cardNo;
        rspBody.name = patient.name;
        rspBody.sex = patient.sex;
        rspBody.age = DateHelper.GetAgeDetail(patient.birthday);
        rspBody.identityCardNo = patient.identityCardNo;
        rspBody.tel = patient.tel;

        return GsonHelper.ToJson(rspBody);
    }
}

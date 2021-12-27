package com.kaos.his.controller;

import javax.validation.constraints.NotEmpty;

import com.kaos.his.service.BaseInfoService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class BaseInfoController {
    /**
     * 基本信息业务
     */
    @Autowired
    BaseInfoService baseInfoService;

    /**
     * 获取患者基本信息
     * 
     * @return
     */
    @RequestMapping(value = "queryPatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String QueryPatient(@NotEmpty(message = "患者卡号不能为空") @RequestParam("cardNo") String cardNo) {
        // 查询实体
        var patient = this.baseInfoService.QueryPatient(cardNo);

        // 构造响应
        return GsonHelper.ToJson(patient);
    }
}
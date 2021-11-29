package com.kaos.his.controller.entity;

import com.kaos.his.service.EntityService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi/entity")
public class GetPatientController {
    /**
     * 实体服务
     */
    @Autowired
    EntityService entityService;

    @RequestMapping(value = "getPatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("cardNo") String cardNo) {
        // 调取业务获取实体
        var patient = this.entityService.QueryPatient(cardNo);

        return GsonHelper.ToJson(patient);
    }
}

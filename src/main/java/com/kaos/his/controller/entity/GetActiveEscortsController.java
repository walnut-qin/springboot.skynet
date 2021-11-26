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
public class GetActiveEscortsController {
    /**
     * 实体服务
     */
    @Autowired
    EntityService entityService;

    @RequestMapping(value = "getActiveEscorts", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("patientCardNo") String patientCardNo) {
        // 调取业务获取实体
        var escortCards = this.entityService.QueryActiveEscorts(patientCardNo);

        return GsonHelper.GetUniversalGson().toJson(escortCards);
    }
}

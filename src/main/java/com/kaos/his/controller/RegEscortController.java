package com.kaos.his.controller;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.service.EscortService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class RegEscortController {
    /**
     * 陪护证业务
     */
    @Autowired
    EscortService escortService;

    /**
     * 获取陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "RegEscort", method = RequestMethod.POST)
    public void Run(@RequestBody String body) {
        // 解析body
        var escortCard = GsonHelper.FromJson(body, EscortCard.class);

        // 添加陪护
        this.escortService.InsertEscort(escortCard);
    }
}

package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;

import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.service.EscortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class UpdateEscortStateController {
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
    @RequestMapping(value = "updateEscortState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public void Run(@RequestParam("escortNo") String escortNo, @RequestParam("newState") EscortStateEnum newState) {
        // 入参检查
        if (escortNo == null || escortNo.isEmpty()) {
            throw new InvalidParameterException("陪护证号不能为空");
        }

        // 执行更新服务
        this.escortService.UpdateEscortState(escortNo, newState);
    }
}

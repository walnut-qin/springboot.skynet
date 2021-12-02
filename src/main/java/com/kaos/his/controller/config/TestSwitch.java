package com.kaos.his.controller.config;

import com.kaos.his.service.ConfigService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi/config")
public class TestSwitch {
    /**
     * 实体服务
     */
    @Autowired
    ConfigService configService;

    @ResponseBody
    @RequestMapping(value = "testSwitch", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String Run(@RequestParam("switchName") String switchName) {
        // 调取业务获取实体
        var state = this.configService.TestSwitch(switchName);

        return state.toString();
    }
}

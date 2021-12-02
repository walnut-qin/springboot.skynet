package com.kaos.his.controller;

import com.kaos.his.service.TestService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class TestController {
    @Autowired
    TestService testService;

    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("param") String param) {
        var entity = this.testService.Run(param);
        return GsonHelper.ToJson(entity);
    }
}

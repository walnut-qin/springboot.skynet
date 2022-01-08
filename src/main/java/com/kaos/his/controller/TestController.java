package com.kaos.his.controller;

import com.kaos.his.service.TestService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class TestController {
    @Autowired
    TestService testService;

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String Run(@RequestParam("param") String param) {
        return GsonHelper.ToJson(this.testService.Run(param));
    }
}

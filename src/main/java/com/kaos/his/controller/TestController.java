package com.kaos.his.controller;

import com.kaos.his.service.DayBalanceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/webApi")
public class TestController {
    @Autowired
    DayBalanceService dayBalanceService;

    @ResponseBody
    @RequestMapping(value = "test", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public ModelAndView Run() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/Hello.html");

        return mv;
    }
}

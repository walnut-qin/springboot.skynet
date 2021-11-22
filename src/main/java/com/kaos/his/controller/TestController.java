package com.kaos.his.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.TestService;

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
    public String Run(@RequestParam("cardNo") String deptCode) {
        var dept = this.testService.GetDepartment(deptCode);

        // 响应json字符串
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class)).create();

        return gson.toJson(dept);
    }
}

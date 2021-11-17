package com.kaos.his.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("getEmployee")
    public String GetEmployee(@RequestParam("emplCode") String emplCode) {
        var employee = this.employeeService.GetEmployeeByEmplCode(emplCode);
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(employee);
    }
}

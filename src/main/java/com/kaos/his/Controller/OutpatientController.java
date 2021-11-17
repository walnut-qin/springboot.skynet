package com.kaos.his.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.OutpatientStateEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.OutpatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class OutpatientController {
    /**
     * Mapper映射
     */
    @Autowired
    private OutpatientService outpatientService;

    /**
     * 获取职工信息
     * 
     * @param emplCode 职工编码
     * @return 职工信息json
     */
    @RequestMapping(value = "getOutpatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String GetOutpatientByClinicCode(@RequestParam("clinicCode") String clinicCode) {
        // 调取service获取职员实体
        var outpatient = this.outpatientService.GetOutpatientByClinicCode(clinicCode);

        // 创建特定类型的gson对象
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .registerTypeAdapter(OutpatientStateEnum.class, new GsonEnumTypeAdapter<>(OutpatientStateEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(outpatient);
    }

    /**
     * 获取职工信息
     * 
     * @param emplCode 职工编码
     * @return 职工信息json
     */
    @RequestMapping(value = "getOutpatients", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String GetOutPatientsByCardNo(@RequestParam("cardNo") String cardNo) {
        // 调取service获取职员实体
        var outpatients = this.outpatientService.GetOutpatientsByCardNo(cardNo);

        // 创建特定类型的gson对象
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .registerTypeAdapter(OutpatientStateEnum.class, new GsonEnumTypeAdapter<>(OutpatientStateEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(outpatients);
    }
}

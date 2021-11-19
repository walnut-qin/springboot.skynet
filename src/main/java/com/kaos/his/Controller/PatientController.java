package com.kaos.his.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.personnel.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class PatientController {
    /**
     * Mapper映射
     */
    @Autowired
    private PatientService patientService;

    /**
     * 获取职工信息
     * 
     * @param emplCode 职工编码
     * @return 职工信息json
     */
    @RequestMapping(value = "getPatient", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String GetEmployee(@RequestParam("cardNo") String cardNo) {
        // 调取service获取职员实体
        var patient = this.patientService.GetPatientByCardNo(cardNo);

        // 创建特定类型的gson对象
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(patient);
    }
}

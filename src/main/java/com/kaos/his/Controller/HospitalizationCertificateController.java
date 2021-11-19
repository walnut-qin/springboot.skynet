package com.kaos.his.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.HospitalizationCertificateStateEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.credential.HospitalizationCertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class HospitalizationCertificateController {
    /**
     * Mapper映射
     */
    @Autowired
    private HospitalizationCertificateService hospitalizationCertificateService;

    /**
     * 获取职工信息
     * 
     * @param emplCode 职工编码
     * @return 职工信息json
     */
    @RequestMapping(value = "getHospitalizationCertificate", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String GetInpatientByClinicCode(@RequestParam("cardNo") String cardNo, @RequestParam("happenNo") Integer happenNo) {
        // 调取service获取职员实体
        var hospitalizationCertificate = this.hospitalizationCertificateService.GetHospitalizationCertificateByCardNoAndHappenNo(cardNo, happenNo);

        // 创建特定类型的gson对象
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .registerTypeAdapter(HospitalizationCertificateStateEnum.class, new GsonEnumTypeAdapter<>(HospitalizationCertificateStateEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(hospitalizationCertificate);
    }
}

package com.kaos.his.controller;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.entity.credential.Escort;
import com.kaos.his.enums.DeptOwnEnum;
import com.kaos.his.enums.EscortActionEnum;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.credential.EscortService;
import com.kaos.his.service.credential.HospitalizationCertificateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class EscortController {
    /**
     * 陪护业务
     */
    @Autowired
    private EscortService escortService;

    /**
     * 住院证业务
     */
    @Autowired
    private HospitalizationCertificateService hospitalizationCertificateService;

    /**
     * 获取职工信息
     * 
     * @param emplCode 职工编码
     * @return 职工信息json
     */
    @RequestMapping(value = "getEscort", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getEscort(@RequestParam("escortNo") String escortNo) {
        // 调取service获取职员实体
        var escort = this.escortService.GetEscortByEscortNo(escortNo);

        // 创建特定类型的gson对象
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .registerTypeAdapter(EscortStateEnum.class, new GsonEnumTypeAdapter<>(EscortStateEnum.class))
                .registerTypeAdapter(EscortActionEnum.class, new GsonEnumTypeAdapter<>(EscortActionEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(escort);
    }

    /**
     * 获取职工信息
     * 
     * @param emplCode 职工编码
     * @return 职工信息json
     */
    @RequestMapping(value = "getEscortedPatients", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getActiveEscortsByHelperCardNo(@RequestParam("helperCardNo") String cardNo) {
        // 获取此人拥有的所有陪护证
        var escorts = this.escortService.GetEscortsByHelperCardNo(cardNo);

        // 从中剔除无效陪护证
        List<Escort> activeEscorts = new ArrayList<Escort>();
        for (Escort escort : escorts) {
            // 若已注销
            if (escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 获取被陪护患者的最后一张住院证
            var lastHosCtf = this.hospitalizationCertificateService
                    .GetLatestHospitalizationCertificateByCardNo(escort.hospitalizationCertificate.cardNo);
            if (lastHosCtf == null || escort.hospitalizationCertificate.happenNo != lastHosCtf.happenNo) {
                continue;
            }

            // 否则就是合法的陪护证，获取这个被陪护的患者
            activeEscorts.add(escort);
        }

        // 创建特定类型的gson对象
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class))
                .registerTypeAdapter(DeptOwnEnum.class, new GsonEnumTypeAdapter<>(DeptOwnEnum.class))
                .registerTypeAdapter(EscortStateEnum.class, new GsonEnumTypeAdapter<>(EscortStateEnum.class))
                .registerTypeAdapter(EscortActionEnum.class, new GsonEnumTypeAdapter<>(EscortActionEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();

        return gson.toJson(activeEscorts);
    }
}

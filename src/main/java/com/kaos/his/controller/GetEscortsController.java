package com.kaos.his.controller;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.EscortService;
import com.kaos.util.DateHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class GetEscortsController {
    /**
     * 陪护系统服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 陪护人信息
     */
    public static class HelperInfo {
        /**
         * 陪护证号
         */
        public String escortNo = null;

        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 免费标识： 0 - 不免费 1 - 免费
         */
        public String freeFlag = null;
    }

    /**
     * 获取患者的陪护人信息
     * 
     * @param cardNo 患者就诊卡号
     * @return
     */
    @RequestMapping(value = "getActiveEscorts", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("cardNo") String cardNo) {
        // 声明结果集
        var resultSet = new ArrayList<HelperInfo>();

        // 调取服务
        var escorts = this.escortService.QueryActiveEscortsByPatient(cardNo);

        // 循环赋值
        for (EscortCard escort : escorts) {
            var helperInfo = new HelperInfo();
            helperInfo.escortNo = escort.escortNo;
            helperInfo.cardNo = escort.helper.cardNo;
            helperInfo.name = escort.helper.name;
            helperInfo.sex = escort.helper.sex;
            helperInfo.age = DateHelper.GetAgeDetail(escort.helper.birthday);
            helperInfo.freeFlag = escort.vip ? "1" : "0";
            resultSet.add(helperInfo);
        }

        // 响应json字符串
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class)).create();
        return gson.toJson(resultSet);
    }
}

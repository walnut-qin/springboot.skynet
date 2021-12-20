package com.kaos.his.controller.escort;

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
public class QueryActiveEscortHelperController {
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
    @RequestMapping(value = "queryActiveEscortHelper", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("patientCardNo") String patientCardNo) {
        // 入参判断
        if (patientCardNo == null) {
            throw new RuntimeException("患者卡号不能为空");
        }

        // 调取服务
        var escorts = this.escortService.QueryPatientRegisteredEscorts(patientCardNo);

        // 循环赋值
        var resultSet = new ArrayList<HelperInfo>();
        for (EscortCard escortCard : escorts) {
            // 创建响应实体元素
            var helperInfo = new HelperInfo();
            helperInfo.escortNo = escortCard.escortNo;
            helperInfo.cardNo = escortCard.helper.cardNo;
            helperInfo.name = escortCard.helper.name;
            helperInfo.sex = escortCard.helper.sex;
            helperInfo.age = DateHelper.GetAgeDetail(escortCard.helper.birthday);
            helperInfo.freeFlag = escortCard.helperCardNo.equals(escortCard.escortVip.helperCardNo) ? "1" : "0";

            // 添加响应实体元素
            resultSet.add(helperInfo);
        }

        // 响应json字符串
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(SexEnum.class, new GsonEnumTypeAdapter<>(SexEnum.class)).create();
        return gson.toJson(resultSet);
    }
}

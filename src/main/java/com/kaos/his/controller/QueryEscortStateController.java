package com.kaos.his.controller;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.util.GsonEnumTypeAdapter;
import com.kaos.his.service.EscortService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class QueryEscortStateController {
    /**
     * 陪护系统服务
     */
    @Autowired
    EscortService escortService;

    /**
     * 被陪护患者信息
     */
    public static class EscortState {
        /**
         * 陪护人卡号
         */
        public String escortCardNo = null;

        /**
         * 患者卡号
         */
        public String patientCardNo = null;

        /**
         * 姓名
         */
        public Date regDate = null;

        /**
         * 状态
         */
        public EscortStateEnum state = null;
    }

    /**
     * 获取陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "queryEscortState", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("escortNo") String escortNo) {
        // 声明结果
        var state = new EscortState();

        // 提取指定陪护证
        var escort = this.escortService.QueryEscort(escortNo);
        if (escort == null) {
            return null;
        }

        // 赋值
        state.escortCardNo = escort.helper.cardNo;
        state.patientCardNo = escort.preinCard.cardNo;
        state.regDate = escort.states.get(0).operDate;
        state.state = escort.states.get(escort.states.size() - 1).state;

        // 响应json字符串
        Gson gson = new GsonBuilder().serializeNulls()
                .registerTypeAdapter(EscortStateEnum.class, new GsonEnumTypeAdapter<>(EscortStateEnum.class))
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(state);
    }
}

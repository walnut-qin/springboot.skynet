package com.kaos.his.controller.escort;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.service.EscortService;
import com.kaos.util.GsonHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class RegEscortController {
    /**
     * 陪护证业务
     */
    @Autowired
    EscortService escortService;

    private List<Object> patientLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    private List<Object> helperLocks = new ArrayList<>() {
        {
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
            add(new Object());
        }
    };

    /**
     * 获取陪护证状态
     * 
     * @param escortNo
     * @return
     */
    @RequestMapping(value = "regEscort", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Run(@RequestParam("patientCardNo") String patient, @RequestParam("helperCardNo") String helper) {
        // 入参判断
        if (patient == null || patient.isEmpty()) {
            throw new InvalidParameterException("患者号不能为空");
        } else if (helper == null || helper.isEmpty()) {
            throw new InvalidParameterException("陪护号不能为空");
        }

        // 声明陪护实体
        EscortCard recEscortCard = null;

        // 加患者锁
        var patientLock = this.patientLocks.get(Integer.valueOf(patient.substring(patient.length() - 3)) % 10);
        synchronized (patientLock) {
            // 加陪护锁
            var helperLock = this.helperLocks.get(Integer.valueOf(helper.substring(helper.length() - 3)) % 10);
            synchronized (helperLock) {
                // 添加陪护
                recEscortCard = this.escortService.InsertEscort(patient, helper);
            }
        }

        return GsonHelper.ToJson(recEscortCard);
    }
}

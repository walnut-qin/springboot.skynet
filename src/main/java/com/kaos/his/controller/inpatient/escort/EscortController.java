package com.kaos.his.controller.inpatient.escort;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.kaos.his.controller.inpatient.escort.entity.QueryStateInfoRspBody;
import com.kaos.his.service.inpatient.EscortService;
import com.kaos.util.GsonHelper;
import com.kaos.util.ListHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/escort")
public class EscortController {
    /**
     * 接口：日志服务
     */
    Logger logger = Logger.getLogger(EscortController.class.getName());

    /**
     * 20个陪护证状态锁
     */
    public static final List<Object> stateLocks = new ArrayList<>() {
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
     * 10个陪护证动作锁
     */
    public static final List<Object> actionLocks = new ArrayList<>() {
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
        }
    };

    /**
     * 10个患者锁
     */
    public static final List<Object> patientLocks = new ArrayList<>() {
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
        }
    };

    /**
     * 10个陪护锁
     */
    public static final List<Object> helperLocks = new ArrayList<>() {
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
        }
    };

    /**
     * 陪护证服务
     */
    @Autowired
    EscortService escortService;

    @ResponseBody
    @RequestMapping(value = "queryStateInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryStateInfo(@NotBlank(message = "陪护证号不能为空") String escortNo) {
        // 记录日志
        this.logger.info(String.format("查询陪护证 %s 的状态", escortNo));

        // 调用服务
        var srvRt = this.escortService.queryEscortStateInfo(escortNo);

        // 构造响应
        var rspBody = new QueryStateInfoRspBody();
        rspBody.patientCardNo = srvRt.patientCardNo;
        rspBody.helperCardNo = srvRt.helperCardNo;
        if (srvRt.associateEntity.stateRecs != null) {
            rspBody.regDate = srvRt.associateEntity.stateRecs.get(0).recDate;
            rspBody.curState = ListHelper.GetLast(srvRt.associateEntity.stateRecs).state;
        }

        return GsonHelper.ToJson(rspBody);
    }
}

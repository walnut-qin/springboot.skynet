package com.kaos.his.controller;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.surgery.MetOpsApply;
import com.kaos.his.enums.AnesTypeEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.SurgeryDegreeEnum;
import com.kaos.his.service.SurgeryService;
import com.kaos.util.GsonHelper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webApi")
public class SurgeryController {
    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(SurgeryController.class.getName());

    /**
     * 手术服务
     */
    @Autowired
    SurgeryService surgeryService;

    @ResponseBody
    @RequestMapping(value = "surgery/queryDeptSurgeries", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String queryDeptSurgeries(@RequestParam("deptCode") String deptCode,
            @RequestParam("beginDate") Date beginDate,
            @RequestParam("endDate") Date endDate) {
        // 入参检查
        if (deptCode == null || deptCode.isEmpty()) {
            throw new RuntimeException("科室编码不能为空");
        } else if (beginDate == null || endDate == null) {
            throw new RuntimeException("起止时间不能为空");
        }

        // 记录日志
        this.logger.info(String.format("查询科室手术(deptCode = %s, beginDate = %s, endDate = %s)", deptCode,
                beginDate.toString(), endDate.toString()));

        // 执行业务
        var rs = this.surgeryService.queryMetOpsApplies(deptCode, beginDate, endDate);

        return GsonHelper.ToJson(rs);
    }

    /**
     * queryDeptSurgeries_rspBody
     */
    public class queryDeptSurgeries_rspBody {
        /**
         * 手术间
         */
        public String roomNo = null;

        /**
         * 手术时间
         */
        public Date surgeryDate = null;

        /**
         * 住院科室
         */
        public String deptCode = null;

        /**
         * 床号
         */
        public String bedNo = null;

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
         * 诊断
         */
        public String diagnosis = null;

        /**
         * 手术名
         */
        public String surgeryName = null;

        /**
         * 备注
         */
        public String remark = null;

        /**
         * 手术等级
         */
        public SurgeryDegreeEnum surgeryDegree = null;

        /**
         * 术者姓名
         */
        public String operDocName = null;

        /**
         * 助手姓名
         */
        public List<String> helperNames = null;

        /**
         * 麻醉方法
         */
        public AnesTypeEnum anesType = null;

        /**
         * 麻醉医生姓名
         */
        public List<String> anesDocNames = null;

        /**
         * 洗手护士
         */
        public List<String> washNurses = null;

        /**
         * 巡回护士列表
         */
        public List<String> itinerantNurses = null;

        /**
         * 特殊需求
         */
        public String specialMessage = null;
    }
}

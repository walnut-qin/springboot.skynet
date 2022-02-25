package com.kaos.his.controller.inpatient.surgery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.kaos.his.controller.inpatient.surgery.entity.QueryAppliesReq;
import com.kaos.his.controller.inpatient.surgery.entity.QueryAppliesRsp;
import com.kaos.his.controller.inpatient.surgery.entity.QueryStatesReq;
import com.kaos.his.controller.inpatient.surgery.entity.QueryStatesRsp;
import com.kaos.his.controller.inpatient.surgery.entity.QueryAppliesRsp.Data;
import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.his.enums.impl.inpatient.surgery.SurgeryArrangeRoleEnum;
import com.kaos.his.service.inf.inpatient.surgery.SurgeryService;
import com.kaos.his.util.DateHelpers;
import com.kaos.his.util.Gsons;
import com.kaos.his.util.HttpHelpers;
import com.kaos.his.util.ListHelpers;
import com.kaos.his.util.helper.DateHelper;
import com.kaos.his.util.helper.HttpHelper;
import com.kaos.his.util.helper.ListHelper;

import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/inpatient/surgery")
public class SurgeryController {
    /**
     * 接口：日志服务
     */
    Logger logger = Logger.getLogger(SurgeryController.class.getName());

    /**
     * gson处理器
     */
    Gson gson = Gsons.newGson();

    /**
     * 基本类型助手
     */
    ListHelper listHelper = ListHelpers.newListHelper();

    /**
     * 时间类型助手
     */
    DateHelper dateHelper = DateHelpers.newDateHelper();

    /**
     * 接口：手术服务
     */
    @Autowired
    SurgeryService surgeryService;

    /**
     * HttpHelper
     */
    HttpHelper httpClient = HttpHelpers.newHttpClient(HttpHelpers.DOCARE_SERVER);

    /**
     * 构造响应体元素
     * 
     * @param item
     * @return
     */
    private Data createQueryMetOpsAppliesInDeptRspBody(MetOpsApply apply) {
        // 创建实体
        var rspBody = new QueryAppliesRsp.Data();

        // 手术间
        if (apply.associateEntity.room != null) {
            rspBody.roomNo = apply.associateEntity.room.roomName;
        } else {
            rspBody.roomNo = apply.roomId;
        }

        // 预约时间
        rspBody.apprDate = apply.apprDate;

        // 住院号
        rspBody.patientNo = apply.patientNo;
        if (apply.associateEntity.inMainInfo != null) {
            // 定位住院实体
            var inMainInfo = apply.associateEntity.inMainInfo;

            // 患者科室
            if (inMainInfo.associateEntity.dept != null) {
                rspBody.deptName = inMainInfo.associateEntity.dept.deptName;
            } else {
                rspBody.deptName = inMainInfo.deptCode;
            }

            // 床号
            if (inMainInfo.associateEntity.bedInfo != null) {
                rspBody.bedNo = inMainInfo.associateEntity.bedInfo.getBriefBedNo();
            } else {
                rspBody.bedNo = inMainInfo.bedNo;
            }

            // 患者姓名、性别、年龄
            if (inMainInfo.associateEntity.patientInfo != null) {
                rspBody.name = inMainInfo.associateEntity.patientInfo.name;
                rspBody.sex = inMainInfo.associateEntity.patientInfo.sex;
                rspBody.age = this.dateHelper.getAge(inMainInfo.associateEntity.patientInfo.birthday).toString();
            } else {
                rspBody.name = inMainInfo.name;
            }

            // ERAS
            rspBody.eras = inMainInfo.erasInpatient != null && inMainInfo.erasInpatient ? "是" : "否";

            // VTE
            rspBody.vte = inMainInfo.vte;
        }

        // 诊断
        rspBody.diagnosis = apply.diagnosis;

        // 手术名称
        if (apply.associateEntity.metOpsItem != null) {
            rspBody.surgeryDocName = apply.associateEntity.metOpsItem.itemName;
        }

        // 手术标识
        rspBody.operRemark = apply.operRemark;

        // 手术级别
        rspBody.degree = apply.degree;

        // 术者
        if (apply.associateEntity.opsDoc != null) {
            rspBody.surgeryDocName = apply.associateEntity.opsDoc.emplName;
        } else {
            rspBody.surgeryDocName = apply.opsDocCode;
        }

        // 手术安排相关
        if (apply.associateEntity.metOpsArranges != null) {
            // 定位手术安排
            var arranges = apply.associateEntity.metOpsArranges;

            // 助手
            List<MetOpsArrange> reg = new ArrayList<>();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper1));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper2));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper3));
            rspBody.helperNames = this.listHelper.join(";", reg.stream().map(new Function<MetOpsArrange, String>() {
                @Override
                public @Nullable String apply(@Nullable MetOpsArrange input) {
                    if (input == null || input.associateEntity.employee == null) {
                        return null;
                    } else {
                        return input.associateEntity.employee.emplName;
                    }
                }
            }).toList());

            // 麻醉医生1
            var arrange = arranges.get(SurgeryArrangeRoleEnum.Anaesthetist);
            if (arrange != null) {
                if (arrange.associateEntity.employee != null) {
                    rspBody.anesDoc1 = arrange.associateEntity.employee.emplName;
                } else {
                    rspBody.anesDoc1 = arrange.emplCode;
                }
            }

            // 麻醉医生2
            arrange = arranges.get(SurgeryArrangeRoleEnum.AnaesthesiaHelper);
            if (arrange != null) {
                if (arrange.associateEntity.employee != null) {
                    rspBody.anesDoc1 = arrange.associateEntity.employee.emplName;
                } else {
                    rspBody.anesDoc1 = arrange.emplCode;
                }
            }

            // 洗手护士
            reg.clear();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.WashingHandNurse));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.WashingHandNurse1));
            rspBody.washNurseNames = this.listHelper.join(";", reg.stream().map(new Function<MetOpsArrange, String>() {
                @Override
                public @Nullable String apply(@Nullable MetOpsArrange input) {
                    if (input == null || input.associateEntity.employee == null) {
                        return null;
                    } else {
                        return input.associateEntity.employee.emplName;
                    }
                }
            }).toList());

            // 巡回护士
            reg.clear();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.ItinerantNurse));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.ItinerantNurse1));
            rspBody.itinerantNurseNames = this.listHelper.join(";",
                    reg.stream().map(new Function<MetOpsArrange, String>() {
                        @Override
                        public @Nullable String apply(@Nullable MetOpsArrange input) {
                            if (input == null || input.associateEntity.employee == null) {
                                return null;
                            } else {
                                return input.associateEntity.employee.emplName;
                            }
                        }
                    }).toList());
        }

        // 麻醉种类
        rspBody.anesType = apply.anesType;

        // 特殊需求
        rspBody.applyNote = apply.applyNote;

        // 检验结果
        rspBody.inspectResult = apply.inspectResult;

        // 是否公布
        rspBody.publishFlag = apply.publishFlag != null && apply.publishFlag ? "是" : "否";

        return rspBody;
    }

    @ResponseBody
    @RequestMapping(value = "queryArrangedApplies", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public QueryAppliesRsp queryArrangedApplies(@RequestBody QueryAppliesReq req) {
        // 记录日志
        this.logger.info("查询手术申请, 入参:");
        this.logger.info(this.gson.toJson(req));

        // 调用服务
        var rs = this.surgeryService.queryApplies(req.deptCode, req.roomNo, req.beginDate, req.endDate, req.states);

        // 记录日志
        this.logger.info(String.format("查询科室手术(count = %d)", rs.size()));

        // 获取手麻系统中的状态
        var reqBody = new QueryStatesReq();
        reqBody.applyNos = rs.stream().map((x) -> {
            return x.operationNo;
        }).toList();
        var stateMap = this.httpClient.postForObject("ms/operation/queryStates", reqBody, QueryStatesRsp.class).states;

        // 构造响应体
        var rspBodies = new QueryAppliesRsp();
        rspBodies.size = rs.size();
        rspBodies.data = Lists.newArrayList();
        for (var item : rs) {
            var rspItem = this.createQueryMetOpsAppliesInDeptRspBody(item);
            if (stateMap.containsKey(item.operationNo)) {
                rspItem.surgeryState = stateMap.get(item.operationNo);
            }
            rspBodies.data.add(rspItem);
        }

        return rspBodies;
    }

    @RequestMapping(value = "queryApplyNo", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String queryApplyNo(@NotBlank(message = "住院号不能为空") String patientNo,
            @NotNull(message = "开始时间不能为空") Date beginDate,
            @NotNull(message = "结束时间不能为空") Date endDate) {
        // 日志
        this.logger.info(String.format("查询手术申请号(patientNo = %s, beginDate = %s, endDate = %s)", patientNo,
                beginDate.toString(), endDate.toString()));

        // 调用业务
        return this.surgeryService.queryValidApplyNo(patientNo, beginDate, endDate);
    }
}

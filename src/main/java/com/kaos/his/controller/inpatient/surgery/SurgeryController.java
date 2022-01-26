package com.kaos.his.controller.inpatient.surgery;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.kaos.helper.type.TypeHelper;
import com.kaos.helper.type.impl.TypeHelperImpl;
import com.kaos.his.controller.inpatient.surgery.entity.QueryArrangedMetOpsAppliesInDeptRspBody;
import com.kaos.his.entity.inpatient.surgery.MetOpsApply;
import com.kaos.his.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.his.entity.inpatient.surgery.MetOpsItem;
import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.his.enums.SurgeryStatusEnum;
import com.kaos.his.service.inpatient.SurgeryService;

import org.apache.log4j.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 基本类型助手
     */
    TypeHelper typeHelper = new TypeHelperImpl();

    /**
     * 接口：手术服务
     */
    @Autowired
    SurgeryService surgeryService;

    @ResponseBody
    @RequestMapping(value = "queryArrangedMetOpsAppliesInDept", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public List<QueryArrangedMetOpsAppliesInDeptRspBody> queryArrangedMetOpsAppliesInDept(
            @RequestParam("deptCode") String deptCode,
            @RequestParam("beginDate") Date beginDate,
            @RequestParam("endDate") Date endDate) {
        // 入参判断
        if (deptCode.isEmpty()) {
            throw new InvalidParameterException("科室编码不能为空");
        }

        // 记录日志
        this.logger.info(
                String.format("查询科室手术(deptCode = %s, beginDate = %s, endDate = %s)", deptCode, beginDate, endDate));

        // 调用服务
        var rs = this.surgeryService.queryMetOpsAppliesInDept(deptCode, beginDate, endDate, new ArrayList<>() {
            {
                add(SurgeryStatusEnum.手术安排);
                add(SurgeryStatusEnum.手术完成);
            }
        });

        // 记录日志
        this.logger.info(String.format("查询科室手术(count = %d)", rs.size()));

        // 构造响应体
        var rspBodies = new ArrayList<QueryArrangedMetOpsAppliesInDeptRspBody>();
        for (var item : rs) {
            rspBodies.add(this.createQueryMetOpsAppliesInDeptRspBody(item));
        }

        return rspBodies;
    }

    /**
     * 构造响应体元素
     * 
     * @param item
     * @return
     */
    private QueryArrangedMetOpsAppliesInDeptRspBody createQueryMetOpsAppliesInDeptRspBody(MetOpsApply item) {
        var rspBody = new QueryArrangedMetOpsAppliesInDeptRspBody();

        rspBody.roomNo = Optional.fromNullable(item.associateEntity.room).or(new MetOpsRoom()).roomName;
        rspBody.apprDate = item.apprDate;
        rspBody.patientNo = item.patientNo;
        if (item.associateEntity.inpatient != null) {
            var inpatient = item.associateEntity.inpatient;
            if (inpatient.associateEntity.stayedDept == null) {
                rspBody.deptName = inpatient.stayedDeptCode;
            } else {
                rspBody.deptName = inpatient.associateEntity.stayedDept.deptName;
            }
            if (inpatient.associateEntity.bed == null) {
                rspBody.bedNo = inpatient.bedNo;
            } else {
                var bed = inpatient.associateEntity.bed;
                rspBody.bedNo = bed.bedNo.replaceFirst("^" + bed.nurseCellCode, "");
            }
            rspBody.name = inpatient.name;
            rspBody.sex = inpatient.sex;
            rspBody.age = this.typeHelper.getAge(inpatient.birthday).toString();
            rspBody.eras = Optional.fromNullable(inpatient.erasFlag).or(false) ? "是" : "否";
            rspBody.vte = inpatient.vteRank;
        }
        rspBody.diagnosis = item.diagnosis;
        rspBody.surgeryName = Optional.fromNullable(item.associateEntity.metOpsItem).or(new MetOpsItem()).itemName;
        rspBody.operRemark = item.operRemark;
        rspBody.degree = item.degree;
        if (item.associateEntity.opsDoctor != null) {
            rspBody.surgeryDocName = item.associateEntity.opsDoctor.emplName;
        } else {
            rspBody.surgeryDocName = item.opsDocCode;
        }
        if (item.associateEntity.metOpsArranges != null) {
            rspBody.helperNames = Collections2
                    .filter(item.associateEntity.metOpsArranges, new Predicate<MetOpsArrange>() {
                        @Override
                        public boolean apply(@Nullable MetOpsArrange input) {
                            switch (input.role) {
                                case Helper1:
                                case Helper2:
                                case Helper3:
                                    return true;

                                default:
                                    break;
                            }
                            return false;
                        }
                    }).stream().map(x -> {
                        if (x.associateEntity.employee == null) {
                            return x.emplCode;
                        } else {
                            return x.associateEntity.employee.emplName;
                        }
                    }).toList();
            rspBody.anesDocNames = Collections2
                    .filter(item.associateEntity.metOpsArranges, new Predicate<MetOpsArrange>() {
                        @Override
                        public boolean apply(@Nullable MetOpsArrange input) {
                            switch (input.role) {
                                case Anaesthetist:
                                case AnaesthesiaHelper:
                                    return true;

                                default:
                                    break;
                            }
                            return false;
                        }
                    }).stream().map(x -> {
                        if (x.associateEntity.employee == null) {
                            return x.emplCode;
                        } else {
                            return x.associateEntity.employee.emplName;
                        }
                    }).toList();
            rspBody.washNurseNames = Collections2
                    .filter(item.associateEntity.metOpsArranges, new Predicate<MetOpsArrange>() {
                        @Override
                        public boolean apply(@Nullable MetOpsArrange input) {
                            switch (input.role) {
                                case WashingHandNurse:
                                case WashingHandNurse1:
                                    return true;

                                default:
                                    break;
                            }
                            return false;
                        }
                    }).stream().map(x -> {
                        if (x.associateEntity.employee == null) {
                            return x.emplCode;
                        } else {
                            return x.associateEntity.employee.emplName;
                        }
                    }).toList();
            rspBody.itinerantNurseNames = Collections2
                    .filter(item.associateEntity.metOpsArranges, new Predicate<MetOpsArrange>() {
                        @Override
                        public boolean apply(@Nullable MetOpsArrange input) {
                            switch (input.role) {
                                case ItinerantNurse:
                                case ItinerantNurse1:
                                    return true;

                                default:
                                    break;
                            }
                            return false;
                        }
                    }).stream().map(x -> {
                        if (x.associateEntity.employee == null) {
                            return x.emplCode;
                        } else {
                            return x.associateEntity.employee.emplName;
                        }
                    }).toList();
        }
        rspBody.anesType = item.anesType;
        rspBody.applyNote = item.applyNote;
        rspBody.inspectResult = item.inpectResult;
        rspBody.publishFlag = Optional.fromNullable(item.publishFlag).or(false) ? "是" : "否";

        return rspBody;
    }
}

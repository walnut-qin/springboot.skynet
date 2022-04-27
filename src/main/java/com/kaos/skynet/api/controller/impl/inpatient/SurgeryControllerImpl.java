package com.kaos.skynet.api.controller.impl.inpatient;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.entity.inpatient.surgery.QuerySurgeryApplies;
import com.kaos.skynet.api.controller.inf.inpatient.SurgeryController;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsArrangeMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsItemMapper;
import com.kaos.skynet.api.service.inf.inpatient.SurgeryService;
import com.kaos.skynet.entity.common.ComPatientInfo;
import com.kaos.skynet.entity.common.DawnOrgDept;
import com.kaos.skynet.entity.common.DawnOrgEmpl;
import com.kaos.skynet.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.skynet.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.skynet.enums.impl.inpatient.surgery.SurgeryArrangeRoleEnum;
import com.kaos.skynet.util.DateHelpers;
import com.kaos.skynet.util.Gsons;
import com.kaos.skynet.util.HttpHelpers;
import com.kaos.skynet.util.helper.HttpHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/inpatient/surgery")
public class SurgeryControllerImpl implements SurgeryController {
    /**
     * gson处理器
     */
    Gson gson = Gsons.newGson();

    /**
     * HttpHelper
     */
    HttpHelper httpClient = HttpHelpers.newHttpClient(HttpHelpers.DOCARE_SERVER);

    /**
     * 接口：手术服务
     */
    @Autowired
    SurgeryService surgeryService;

    /**
     * 手术申请记录表查询接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 手术项目表查询接口
     */
    @Autowired
    MetOpsItemMapper metOpsItemMapper;

    /**
     * 手术安排接口
     */
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    /**
     * 手术间缓存
     */
    @Autowired
    Cache<String, MetOpsRoom> roomCache;

    /**
     * 职工信息缓存
     */
    @Autowired
    Cache<String, DawnOrgEmpl> emplCache;

    /**
     * 科室信息缓存
     */
    @Autowired
    Cache<String, DawnOrgDept> deptCache;

    /**
     * 住院主表缓存
     */
    @Autowired
    Cache<String, FinIprInMainInfo> inMainInfoCache;

    /**
     * 患者基本信息缓存
     */
    @Autowired
    Cache<String, ComPatientInfo> comPatientInfoCache;

    /**
     * 构造响应体元素
     * 
     * @param item
     * @return
     */
    private QueryAppliesRsp.Data createQueryMetOpsAppliesInDeptRspBody(MetOpsApply apply) {
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
                rspBody.age = DateHelpers.getAge(inMainInfo.associateEntity.patientInfo.birthday).toString();
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
            rspBody.surgeryName = apply.associateEntity.metOpsItem.itemName;
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
            List<MetOpsArrange> reg = Lists.newArrayList();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper1));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper2));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.Helper3));
            reg.removeIf(Objects::isNull);
            rspBody.helperNames = reg.stream().map(x -> {
                if (x == null || x.associateEntity.employee == null) {
                    return null;
                } else {
                    return x.associateEntity.employee.emplName;
                }
            }).collect(Collectors.joining(";"));

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
                    rspBody.anesDoc2 = arrange.associateEntity.employee.emplName;
                } else {
                    rspBody.anesDoc2 = arrange.emplCode;
                }
            }

            // 洗手护士
            reg.clear();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.WashingHandNurse));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.WashingHandNurse1));
            reg.removeIf(Objects::isNull);
            rspBody.washNurseNames = reg.stream().map(x -> {
                if (x == null || x.associateEntity.employee == null) {
                    return null;
                } else {
                    return x.associateEntity.employee.emplName;
                }
            }).collect(Collectors.joining(";"));

            // 巡回护士
            reg.clear();
            reg.add(arranges.get(SurgeryArrangeRoleEnum.ItinerantNurse));
            reg.add(arranges.get(SurgeryArrangeRoleEnum.ItinerantNurse1));
            reg.removeIf(Objects::isNull);
            rspBody.itinerantNurseNames = reg.stream().map(x -> {
                if (x == null || x.associateEntity.employee == null) {
                    return null;
                } else {
                    return x.associateEntity.employee.emplName;
                }
            }).collect(Collectors.joining(";"));
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

    @Override
    @RequestMapping(value = "querySurgeries", method = RequestMethod.POST, produces = MediaType.JSON)
    public QueryAppliesRsp querySurgeries(@RequestBody @Valid QueryAppliesReq req) {
        // 记录日志
        log.info("查询手术申请, 入参:");
        log.info(this.gson.toJson(req));

        // 调用服务
        var rs = this.surgeryService.queryApplies(req.deptCode, req.roomNo, req.beginDate, req.endDate, req.states);

        // 记录日志
        log.info(String.format("查询科室手术(count = %d)", rs.size()));

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

    /**
     * Web请求Body
     */
    public class QueryStatesReq {
        /**
         * 手术申请号
         */
        public List<String> applyNos = null;
    }

    /**
     * Web响应body
     */
    public class QueryStatesRsp {
        /**
         * 状态清单
         */
        public Map<String, String> states = null;
    }

    /**
     * 手术实体映射
     * 
     * @param apply
     * @return
     */
    private QuerySurgeryApplies.Response.DataItem mapToDataItem(MetOpsApply apply) {
        // 声明结果实体
        QuerySurgeryApplies.Response.DataItem result = new QuerySurgeryApplies.Response.DataItem();

        // 定义手术安排通用接口
        interface IEmplHelper {
            String getName(String code);

            String getName(SurgeryArrangeRoleEnum role);
        }
        IEmplHelper emplHelper = new IEmplHelper() {
            List<MetOpsArrange> arranges = metOpsArrangeMapper.queryMetOpsArranges(apply.operationNo,
                    Lists.newArrayList(SurgeryArrangeRoleEnum.Anaesthetist,
                            SurgeryArrangeRoleEnum.AnaesthesiaHelper,
                            SurgeryArrangeRoleEnum.WashingHandNurse,
                            SurgeryArrangeRoleEnum.WashingHandNurse1,
                            SurgeryArrangeRoleEnum.ItinerantNurse,
                            SurgeryArrangeRoleEnum.ItinerantNurse1,
                            SurgeryArrangeRoleEnum.Helper1,
                            SurgeryArrangeRoleEnum.Helper2,
                            SurgeryArrangeRoleEnum.Helper3));

            Map<SurgeryArrangeRoleEnum, MetOpsArrange> arrangeMap = Maps.uniqueIndex(arranges, (x) -> {
                return x.role;
            });

            @Override
            public String getName(String code) {
                var employee = emplCache.getValue(code);
                if (employee != null) {
                    return employee.emplName;
                } else {
                    return code;
                }
            }

            @Override
            public String getName(SurgeryArrangeRoleEnum role) {
                var entity = arrangeMap.get(role);
                if (entity != null) {
                    return this.getName(entity.emplCode);
                }
                return null;
            }
        };

        // 查询手术间
        var room = this.roomCache.getValue(apply.roomId);
        if (room != null) {
            result.setRoomNo(room.roomName);
        }

        // 手术时间
        if (apply.apprDate == null || apply.apprDate.equals(LocalDateTime.of(1, 1, 1, 0, 0, 0))) {
            result.setPreDate(apply.preDate.toLocalTime());
        } else {
            result.setPreDate(apply.apprDate.toLocalTime());
        }

        // 手术类型
        result.setSurgeryKind(apply.surgeryKind);

        // 首台
        result.setFirstFlag(Optional.fromNullable(apply.getFirstFlag()).or(false));

        // 病区
        if (apply.getInDeptCode() != null) {
            var dept = this.deptCache.getValue(apply.getInDeptCode());
            if (dept != null) {
                result.setDeptName(dept.deptName);
            }
        }

        // 住院号
        result.setPatientNo(apply.patientNo);
        // 获取住院实体
        var inMainInfo = this.inMainInfoCache.getValue("ZY01".concat(apply.patientNo));
        if (inMainInfo != null) {
            // 姓名
            result.setName(inMainInfo.name);

            // 性别
            result.setSex(inMainInfo.sex);

            // 年龄
            Period period = inMainInfo.birthday.toLocalDate().until(LocalDate.now());
            Integer y = period.getYears();
            Integer m = period.getMonths();
            Integer d = period.getDays();
            result.setAge(
                    String.format("%s%s%s%s%s%s", y, y == 0 ? "" : "岁", m, m == 0 ? "" : "月", d, d == 0 ? "" : "天"));
        }

        // 台次
        result.setOrder(apply.getOrder());

        // 术前诊断
        result.setPreDiagnosis(apply.diagnosis);

        // 查询手术名称
        var opsItem = this.metOpsItemMapper.queryMetOpsItem(apply.operationNo, "S991");
        if (opsItem != null) {
            result.setSurgeryName(opsItem.itemName);
        }

        // 是否非计划手术
        result.setUnplannedFlag(Optional.fromNullable(apply.unplannedFlag).or(false));

        // 家属已签字
        result.setSignedFlag(Optional.fromNullable(apply.signedFlag).or(false));

        // 麻醉方式
        result.setAnesType(apply.anesType);

        // 手术医生
        result.setSurgeryDoctor(emplHelper.getName(apply.opsDocCode));

        // 主麻
        result.setMasterAnesDoctor(emplHelper.getName(SurgeryArrangeRoleEnum.Anaesthetist));

        // 副麻
        result.setSlaveAnesDoctor(emplHelper.getName(SurgeryArrangeRoleEnum.AnaesthesiaHelper));

        // 洗一
        result.setMasterWashNurse(emplHelper.getName(SurgeryArrangeRoleEnum.WashingHandNurse));

        // 洗二
        result.setSlaveWashNurse(emplHelper.getName(SurgeryArrangeRoleEnum.WashingHandNurse1));

        // 巡一
        result.setMasterItinerantNurse(emplHelper.getName(SurgeryArrangeRoleEnum.ItinerantNurse));

        // 巡二
        result.setSlaveItinerantNurse(emplHelper.getName(SurgeryArrangeRoleEnum.ItinerantNurse1));

        // 特殊要求
        result.setSpecialNote(apply.applyNote);

        // 检验结果
        result.setInspectResult(apply.inspectResult);

        // 手术等级
        result.setSurgeryDegree(apply.degree);

        // 切口等级
        result.setIncisionType(apply.inciType);

        // 助一
        result.setHelper1(emplHelper.getName(SurgeryArrangeRoleEnum.Helper1));

        // 助二
        result.setHelper2(emplHelper.getName(SurgeryArrangeRoleEnum.Helper2));

        // 助三
        result.setHelper3(emplHelper.getName(SurgeryArrangeRoleEnum.Helper3));

        // 审批人
        result.setApprEmpl(emplHelper.getName(apply.apprDocCode));

        // 审批时间
        result.setApprDate(apply.apprDate);

        // 审批意见
        result.setApprNote(apply.apprNote);

        // 手术名备注
        result.setSurgeryNameNote(apply.surgeryNameNote);

        // ERAS
        result.setEras(Optional.fromNullable(inMainInfo.erasInpatient).or(false));

        // VTE
        result.setVte(inMainInfo.vte);

        return result;
    }

    @Override
    @RequestMapping(value = "querySurgeryApplies", method = RequestMethod.POST, produces = MediaType.JSON)
    public QuerySurgeryApplies.Response querySurgeryApplies(@RequestBody @Valid QuerySurgeryApplies.Request req) {
        // 入参记录
        log.info(String.format("查询手术申请记录: %s", this.gson.toJson(req)));

        // 检索原始结果
        var resultSet = this.metOpsApplyMapper.queryApplies(req.getLoginDeptCode(), req.getBeginPreDate(),
                req.getEndPreDate(), req.getExecStatus(), req.getAnesFlag(), req.getValid());

        // 数据集转换
        List<QuerySurgeryApplies.Response.DataItem> data = resultSet.stream().map((x) -> {
            return this.mapToDataItem(x);
        }).sorted((x, y) -> {
            Integer cmpRt = 0;

            // 优先按照手术室排序
            cmpRt = Optional.fromNullable(x.getRoomNo()).or("Z")
                    .compareTo(Optional.fromNullable(y.getRoomNo()).or("Z"));
            if (!cmpRt.equals(0)) {
                return cmpRt;
            }

            // 再按照手术时间排序
            cmpRt = Optional.fromNullable(x.getPreDate()).or(LocalTime.MAX)
                    .compareTo(Optional.fromNullable(y.getPreDate()).or(LocalTime.MAX));
            if (!cmpRt.equals(0)) {
                return cmpRt;
            }

            return 0;
        }).toList();

        // 添加编号
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setNo(i + 1);
        }

        // 构造响应body
        QuerySurgeryApplies.Response body = new QuerySurgeryApplies.Response();
        body.setSize(data.size());
        body.setData(data);

        return body;
    }
}

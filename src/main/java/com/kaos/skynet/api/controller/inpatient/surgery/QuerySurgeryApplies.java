package com.kaos.skynet.api.controller.inpatient.surgery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.controller.MediaType;
import com.kaos.skynet.api.controller.impl.AbstractController;
import com.kaos.skynet.api.controller.inpatient.surgery.QuerySurgeryApplies.Response.DataItem;
import com.kaos.skynet.api.data.cache.common.DawnOrgDeptCache;
import com.kaos.skynet.api.data.cache.common.DawnOrgEmplCache;
import com.kaos.skynet.api.data.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.enums.DeptOwnEnum;
import com.kaos.skynet.api.data.enums.SexEnum;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsApply;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsArrange;
import com.kaos.skynet.api.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.skynet.api.enums.common.ValidStateEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.AnesTypeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.MetOpsInciTypeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryArrangeRoleEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryDegreeEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryInspectResultEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryKindEnum;
import com.kaos.skynet.api.enums.inpatient.surgery.SurgeryStatusEnum;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsApplyMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsArrangeMapper;
import com.kaos.skynet.api.mapper.inpatient.surgery.MetOpsItemMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/inpatient/surgery")
public class QuerySurgeryApplies extends AbstractController {
    /**
     * 手术请求接口
     */
    @Autowired
    MetOpsApplyMapper metOpsApplyMapper;

    /**
     * 手术安排接口
     */
    @Autowired
    MetOpsArrangeMapper metOpsArrangeMapper;

    /**
     * 手术记录项目接口
     */
    @Autowired
    MetOpsItemMapper metOpsItemMapper;

    /**
     * 员工信息缓存
     */
    @Autowired
    DawnOrgEmplCache emplCache;

    /**
     * 科室信息缓存
     */
    @Autowired
    DawnOrgDeptCache deptCache;

    /**
     * 手术室信息缓存
     */
    @Autowired
    Cache<String, MetOpsRoom> roomCache;

    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoCache.MasterCache inMainInfoCache;

    /**
     * 查询手术申请记录
     * 
     * @param req
     * @return
     */
    @RequestMapping(value = "querySurgeryApplies", method = RequestMethod.POST, produces = MediaType.JSON)
    public Response querySurgeryApplies(@RequestBody @Valid Request req) {
        // 入参记录
        log.info(String.format("查询手术申请记录: %s", this.gson.toJson(req)));

        // 检索原始结果
        var resultSet = this.metOpsApplyMapper.queryApplies(req.getLoginDeptCode(), req.getBeginPreDate(),
                req.getEndPreDate(), req.getExecStatus(), req.getAnesFlag(), req.getValid(), req.getDeptOwn());

        // 数据集转换
        List<DataItem> data = resultSet.stream().map((x) -> {
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

    /**
     * 将手术申请信息映射为响应消息体
     * 
     * @param orgData
     * @return
     */
    private DataItem mapToDataItem(MetOpsApply apply) {
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
                var employee = emplCache.get(code);
                if (employee != null) {
                    return employee.getEmplName();
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
            var dept = this.deptCache.get(apply.getInDeptCode());
            if (dept != null) {
                result.setDeptName(dept.getDeptName());
            }
        }

        // 住院号
        result.setPatientNo(apply.patientNo);
        // 获取住院实体
        var inMainInfo = this.inMainInfoCache.get("ZY01".concat(apply.patientNo));
        if (inMainInfo != null) {
            // 姓名
            result.setName(inMainInfo.getName());

            // 性别
            result.setSex(inMainInfo.getSex());

            // 年龄
            result.setAge(Period.between(inMainInfo.getBirthday().toLocalDate(), LocalDate.now()));
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
        result.setEras(Optional.fromNullable(inMainInfo.getErasInpatient()).or(false));

        // VTE
        result.setVte(inMainInfo.getVte());

        // ICU flag
        result.setIcuFlag(apply.getIcuFlag());

        return result;
    }

    /**
     * 请求消息体
     */
    public static class Request {
        /**
         * 登入科室编码
         */
        @Getter
        private String loginDeptCode = null;

        /**
         * 申请时间左值
         */
        @Getter
        @NotNull(message = "申请时间左值不能为空")
        private LocalDateTime beginPreDate = null;

        /**
         * 申请时间右值
         */
        @Getter
        @NotNull(message = "申请时间右值不能为空")
        private LocalDateTime endPreDate = null;

        /**
         * 执行状态列表
         */
        @Getter
        private List<SurgeryStatusEnum> execStatus = null;

        /**
         * 是否已麻醉
         */
        @Getter
        private Boolean anesFlag = null;

        /**
         * 有效标识
         */
        @Getter
        private ValidStateEnum valid = null;

        /**
         * 院区标识
         */
        @Getter
        private DeptOwnEnum deptOwn = null;
    }

    /**
     * 响应消息体
     */
    public static class Response {
        /**
         * 结果集数量
         */
        @Setter
        private Integer size = null;

        /**
         * 数据集
         */
        @Setter
        private List<DataItem> data = null;

        /**
         * 数据项
         */
        public static class DataItem {
            /**
             * 序号
             */
            @Setter
            private Integer No = null;

            /**
             * 手术间
             */
            @Getter
            @Setter
            private String roomNo = null;

            /**
             * 预约时间
             */
            @Getter
            @Setter
            private LocalTime preDate = null;

            /**
             * 手术类型
             */
            @Setter
            private SurgeryKindEnum surgeryKind = null;

            /**
             * 是否为首台
             */
            @Setter
            private Boolean firstFlag = null;

            /**
             * 患者住院科室
             */
            @Setter
            private String deptName = null;

            /**
             * 住院号
             */
            @Setter
            private String patientNo = null;

            /**
             * 患者姓名
             */
            @Setter
            private String name = null;

            /**
             * 性别
             */
            @Setter
            private SexEnum sex = null;

            /**
             * 年龄
             */
            @Setter
            private Period age = null;

            /**
             * 台次
             */
            @Setter
            private String order = null;

            /**
             * 术前诊断
             */
            @Setter
            private String preDiagnosis = null;

            /**
             * 手术名
             */
            @Setter
            private String surgeryName = null;

            /**
             * 非计划手术标识
             */
            @Setter
            private Boolean unplannedFlag = null;

            /**
             * 已申请标识
             */
            @Setter
            private Boolean signedFlag = null;

            /**
             * 麻醉方式
             */
            @Setter
            private AnesTypeEnum anesType = null;

            /**
             * 手术医生姓名
             */
            @Setter
            private String surgeryDoctor = null;

            /**
             * 主麻
             */
            @Setter
            private String masterAnesDoctor = null;

            /**
             * 副麻
             */
            @Setter
            private String slaveAnesDoctor = null;

            /**
             * 主洗手护士
             */
            @Setter
            private String masterWashNurse = null;

            /**
             * 副洗手护士
             */
            @Setter
            private String slaveWashNurse = null;

            /**
             * 主巡回护士
             */
            @Setter
            private String masterItinerantNurse = null;

            /**
             * 副巡回护士
             */
            @Setter
            private String slaveItinerantNurse = null;

            /**
             * 特殊需求
             */
            @Setter
            private String specialNote = null;

            /**
             * 检验结果
             */
            @Setter
            private SurgeryInspectResultEnum inspectResult = null;

            /**
             * 手术等级
             */
            @Setter
            private SurgeryDegreeEnum surgeryDegree = null;

            /**
             * 切口类型
             */
            @Setter
            private MetOpsInciTypeEnum incisionType = null;

            /**
             * 一助
             */
            @Setter
            private String helper1 = null;

            /**
             * 二助
             */
            @Setter
            private String helper2 = null;

            /**
             * 三助
             */
            @Setter
            private String helper3 = null;

            /**
             * 审批人
             */
            @Setter
            private String apprEmpl = null;

            /**
             * 审批时间
             */
            @Setter
            private LocalDateTime apprDate = null;

            /**
             * 审批意见
             */
            @Setter
            private String apprNote = null;

            /**
             * 手术二
             */
            @Setter
            private String surgeryNameNote = null;

            /**
             * ERAS标识
             */
            @Setter
            private Boolean eras = null;

            /**
             * VTE等级
             */
            @Setter
            private String vte = null;

            /**
             * 是否计划术后转入ICU
             */
            @Setter
            private Boolean icuFlag = null;
        }
    }
}

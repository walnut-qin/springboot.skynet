package com.kaos.skynet.api.logic.controller.inpatient.surgery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.docare.entity.medsurgery.MedAnesthesiaPlan.AsaGradeEnum;
import com.kaos.skynet.api.data.docare.entity.medsurgery.MedOperationMaster.OperStatusEnum;
import com.kaos.skynet.api.data.docare.mapper.medsurgery.MedAnesthesiaPlanMapper;
import com.kaos.skynet.api.data.docare.mapper.medsurgery.MedOperationMasterMapper;
import com.kaos.skynet.api.data.his.cache.inpatient.FinIprInMainInfoCache;
import com.kaos.skynet.api.data.his.entity.inpatient.surgery.SurgeryDict.SurgeryLevelEnum;
import com.kaos.skynet.api.data.his.enums.SexEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDeptPrivMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryDictMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.surgery.SurgeryEmplPrivMapper;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@Validated
@CrossOrigin
@RestController
@RequestMapping("/api/inpatient/surgery")
public class SurgeryController {
    /**
     * 科室授权接口
     */
    @Autowired
    SurgeryDeptPrivMapper surgeryDeptPrivMapper;

    /**
     * 人员授权接口
     */
    @Autowired
    SurgeryEmplPrivMapper surgeryEmplPrivMapper;

    /**
     * 手术字典接口
     */
    @Autowired
    SurgeryDictMapper surgeryDictMapper;

    /**
     * 手麻系统手术主表接口
     */
    @Autowired
    MedOperationMasterMapper medOperationMasterMapper;

    /**
     * 手麻系统麻醉计划接口
     */
    @Autowired
    MedAnesthesiaPlanMapper medAnesthesiaPlanMapper;

    /**
     * 住院主表缓存
     */
    @Autowired
    FinIprInMainInfoCache inMainInfoCache;

    /**
     * 查询已经授权的手术清单
     * 
     * @return
     */
    @PassToken
    @ApiName("查询指定科室的授权手术")
    @RequestMapping(value = "queryGrantedSurgerys", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QueryGrantedSurgerys.RspBody> queryGrantedSurgerys(@RequestBody @Valid QueryGrantedSurgerys.ReqBody reqBody) {
        // 检索出该科室的所有手术
        var deptBuilder = SurgeryDeptPrivMapper.Key.builder();
        deptBuilder.deptCode(reqBody.deptCode);
        deptBuilder.valid(true);
        var surgeryDeptPrivs = surgeryDeptPrivMapper.querySurgeryDeptPrivs(deptBuilder.build());

        // 过滤出ICD编码
        var icdCodes = surgeryDeptPrivs.stream().map(x -> {
            return x.getIcdCode();
        }).distinct();

        // 检索有效手术字典
        var surgerys = icdCodes.map(x -> {
            var v = surgeryDictMapper.querySurgeryDict(x);
            if (!v.getValid()) {
                return null;
            }
            return v;
        }).filter(Objects::nonNull);

        var rspBuilder = QueryGrantedSurgerys.RspBody.builder();
        var emplBuilder = SurgeryEmplPrivMapper.Key.builder().valid(true);
        return surgerys.map(x -> {
            rspBuilder.OPR_ID(x.getIcdCode());
            rspBuilder.OPR_NAME(x.getSurgeryName());
            rspBuilder.OPR_LEVEL(x.getSurgeryLevel());
            rspBuilder.PYM(x.getTeleprompter());
            // 术者信息
            var emplInfos = surgeryEmplPrivMapper.querySurgeryEmplPrivs(emplBuilder.icdCode(x.getIcdCode()).build());
            rspBuilder.OPR_DOC = String.join(",", emplInfos.stream().map(y -> {
                return y.getEmplCode();
            }).toList());
            // 科室信息
            rspBuilder.DEPT_CODE = reqBody.deptCode;
            return rspBuilder.build();
        }).toList();
    }

    static class QueryGrantedSurgerys {
        static class ReqBody {
            /**
             * 拟申请手术科室
             */
            String deptCode;
        }

        @Builder
        static class RspBody {
            /**
             * 手术编码
             */
            String OPR_ID;

            /**
             * 手术名称
             */
            String OPR_NAME;

            /**
             * 手术分级
             */
            SurgeryLevelEnum OPR_LEVEL;

            /**
             * 拼音码
             */
            String PYM;

            /**
             * 术者
             */
            String OPR_DOC;

            /**
             * 科室编码
             */
            String DEPT_CODE;
        }
    }

    /**
     * 查询已经授权的手术清单
     * 
     * @return
     */
    @ApiName("查询手术信息")
    @RequestMapping(value = "querySurgeryInfos", method = RequestMethod.POST, produces = MediaType.JSON)
    List<QuerySurgeryInfos.RspBody> querySurgeryInfos(@RequestBody @Valid QuerySurgeryInfos.ReqBody reqBody) {
        var keyBuilder = MedOperationMasterMapper.Key.builder();
        keyBuilder.negOperStatus(Lists.newArrayList(OperStatusEnum.手术取消));
        keyBuilder.beginInDateTime(reqBody.beginInDateTime);
        keyBuilder.endInDateTime(reqBody.endInDateTime);
        keyBuilder.patientId(reqBody.patientNo);
        keyBuilder.levels(reqBody.levels);
        keyBuilder.stayedDeptCodes(reqBody.stayedDeptCodes);
        var result = medOperationMasterMapper.queryOperationMasters(keyBuilder.build());

        var rspBuilder = QuerySurgeryInfos.RspBody.builder();
        return result.stream().map(x -> {
            rspBuilder.date(x.getInDateTime().toLocalDate());
            rspBuilder.roomNo(x.getOperatingRoomNo());
            rspBuilder.surgeryName(x.getOperationName());
            rspBuilder.level(x.getOperationScale());
            var anesPlan = medAnesthesiaPlanMapper.queryAnesthesiaPlan(x.getPatientId(), x.getVisitId(), x.getOperId());
            if (anesPlan != null) {
                rspBuilder.asaGrade(anesPlan.getAsaGrade());
            }
            rspBuilder.inciType(x.getInciType());
            rspBuilder.anesName(x.getAnesName());
            rspBuilder.patientNo(x.getPatientId());
            var patient = inMainInfoCache.get("ZY01".concat(x.getPatientId()));
            if (patient != null) {
                rspBuilder.name(patient.getName());
                rspBuilder.sex(patient.getSex());
                rspBuilder.age(Period.between(patient.getBirthday().toLocalDate(), LocalDate.now()));
                rspBuilder.deptStayed(patient.getDeptName());
            }
            return rspBuilder.build();
        }).toList();
    }

    static class QuerySurgeryInfos {
        static class ReqBody {
            /**
             * 开始时间
             */
            LocalDateTime beginInDateTime;

            /**
             * 开始时间
             */
            LocalDateTime endInDateTime;

            /**
             * 住院号
             */
            String patientNo;

            /**
             * 手术等级
             */
            List<SurgeryLevelEnum> levels;

            /**
             * 住院科室
             */
            List<String> stayedDeptCodes;

            /**
             * ASA分级
             */
            List<AsaGradeEnum> asaGrades;
        }

        @Builder
        static class RspBody {
            /**
             * 入手术室时间
             */
            LocalDate date;

            /**
             * 手术间号
             */
            String roomNo;

            /**
             * 手术名称
             */
            String surgeryName;

            /**
             * ASA分级
             */
            AsaGradeEnum asaGrade;

            /**
             * 手术等级
             */
            SurgeryLevelEnum level;

            /**
             * 切口类型
             */
            String inciType;

            /**
             * 麻醉名称
             */
            String anesName;

            /**
             * 麻醉类型
             */
            String anesType;

            /**
             * 住院号
             */
            String patientNo;

            /**
             * 患者姓名
             */
            String name;

            /**
             * 性别
             */
            SexEnum sex;

            /**
             * 年龄
             */
            Period age;

            /**
             * 住院科室
             */
            String deptStayed;

            /**
             * 主刀医师
             */
            String doctor;

            /**
             * 一助
             */
            String helper1;

            /**
             * 二助
             */
            String helper2;

            /**
             * 三助
             */
            String helper3;

            /**
             * 四助
             */
            String helper4;

            /**
             * 主麻
             */
            String masterAnesDoctor;

            /**
             * 副麻
             */
            String slaveAnesDoctor;

            /**
             * 洗1
             */
            String washNurse1;

            /**
             * 洗2
             */
            String washNurse2;

            /**
             * 巡1
             */
            String itinerantNurse1;

            /**
             * 巡2
             */
            String itinerantNurse2;

            /**
             * 入手术室时间
             */
            LocalDateTime inDateTime;

            /**
             * 麻醉开始时间
             */
            LocalDateTime anesStartDateTime;

            /**
             * 手术开始时间
             */
            LocalDateTime startDateTime;

            /**
             * 手术结束时间
             */
            LocalDateTime endDateTime;

            /**
             * 麻醉结束时间
             */
            LocalDateTime anesEndDateTime;

            /**
             * 出手术室时间
             */
            LocalDateTime outDateTime;
        }
    }
}

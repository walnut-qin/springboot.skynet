package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.cache.inpatient.FinSpecialCityPatientCache;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo.InStateEnum;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.his.enums.HealthCodeEnum;
import com.kaos.skynet.api.data.his.enums.TravelCodeEnum;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.his.tunnel.BedNoTunnel;
import com.kaos.skynet.api.data.his.tunnel.NatsTunnel;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.config.spring.net.RspWrapper;
import com.kaos.skynet.core.util.json.adapter.BooleanTypeAdapter_是否;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@PassToken
@Log4j
@Validated
@RestController
@RequestMapping({ "/api/inpatient/escort/statistic", "/ms/inpatient/escort/statistic" })
public class StatisticController {
    /**
     * 住院主表接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 陪护主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 特殊信息缓存
     */
    @Autowired
    FinSpecialCityPatientCache specialCityPatientCache;

    /**
     * 特殊信息缓存
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 核酸检测
     */
    @Autowired
    NatsTunnel natsTunnel;

    /**
     * 床号转缩略床号
     */
    @Autowired
    BedNoTunnel bedNoTunnel;

    /**
     * 查询科室的患者及陪护基本信息
     * 
     * @param deptCode
     * @return
     */
    @RequestMapping(value = "queryEscortData", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryEscortRsp> queryEscortData(@NotNull(message = "科室编码不能为空") String deptCode) {
        // 日志
        log.info(String.format("查询科室患者及陪护信息(deptCode = %s)", deptCode));

        // 查询指定科室的在院患者
        var pats = inMainInfoMapper.queryInMainInfos(
                FinIprInMainInfoMapper.Key.builder()
                        .deptCode(deptCode)
                        .states(Lists.newArrayList(InStateEnum.病房接诊)).build());

        // 过滤部分患者
        var filteredPats = pats.stream().filter(x -> {
            // 检索特殊标识
            var specialFlag = specialCityPatientCache.get(x.getInpatientNo());
            if (specialFlag != null) {
                switch (Optional.fromNullable(specialFlag.getIsSpecial()).or("0")) {
                    case "0":
                        break;

                    default:
                        return false;
                }
            }
            // 门诊医保
            if (Optional.fromNullable(x.getExtFlag2()).or("0").equals("4")) {
                return false;
            }
            return true;
        }).toList();

        return filteredPats.stream().map(x -> {
            var builder = QueryEscortRsp.builder();
            // 患者信息
            builder.inDate(x.getInDate());
            builder.bedNo(bedNoTunnel.tunneling(x.getBedNo()));
            builder.name(x.getName());
            builder.cardNo(x.getCardNo());
            var patientInfo = patientInfoCache.get(x.getCardNo());
            if (patientInfo != null) {
                builder.healthCode(patientInfo.getHealthCode());
                builder.travelCode(patientInfo.getTravelCode());
                builder.highRiskFlag(patientInfo.getHighRiskFlag());
                builder.highRiskArea(patientInfo.getHighRiskArea());
            }
            var nats = natsTunnel.tunneling(NatsTunnel.Key.builder()
                    .cardNos(Lists.newArrayList(x.getCardNo(), x.getPatientNo()))
                    .duration(Duration.ofDays(14))
                    .build());
            if (nats != null) {
                builder.nucleicAcidResult(nats.toString());
            }
            // 陪护信息
            var escortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                    .patientCardNo(x.getCardNo())
                    .states(Lists.newArrayList(
                            StateEnum.无核酸检测结果,
                            StateEnum.等待院内核酸检测结果,
                            StateEnum.等待院外核酸检测结果审核,
                            StateEnum.生效中,
                            StateEnum.其他))
                    .build());
            if (escortInfos.size() >= 1) {
                var escort = escortInfos.get(0);
                var helper = patientInfoCache.get(escort.getHelperCardNo());
                if (helper != null) {
                    builder.escort1Name(helper.getName());
                    builder.escort1CardNo(helper.getCardNo());
                    builder.escort1IdenNo(helper.getIdentityCardNo());
                    var helperNats = natsTunnel.tunneling(NatsTunnel.Key.builder()
                            .cardNos(Lists.newArrayList(helper.getCardNo()))
                            .duration(Duration.ofDays(14))
                            .build());
                    if (helperNats != null) {
                        builder.escort1NucleicAcidResult(helperNats.toString());
                    }
                    builder.escort1Tel(helper.getLinkmanTel());
                    builder.escort1HealthCode(helper.getHealthCode());
                    builder.escort1TravelCode(helper.getTravelCode());
                    builder.escort1HighRiskFlag(helper.getHighRiskFlag());
                    builder.escort1HighRiskArea(helper.getHighRiskArea());
                }
            }
            if (escortInfos.size() >= 2) {
                var escort = escortInfos.get(1);
                var helper = patientInfoCache.get(escort.getHelperCardNo());
                if (helper != null) {
                    builder.escort2Name(helper.getName());
                    builder.escort2CardNo(helper.getCardNo());
                    builder.escort2IdenNo(helper.getIdentityCardNo());
                    var helperNats = natsTunnel.tunneling(NatsTunnel.Key.builder()
                            .cardNos(Lists.newArrayList(helper.getCardNo()))
                            .duration(Duration.ofDays(14))
                            .build());
                    if (helperNats != null) {
                        builder.escort2NucleicAcidResult(helperNats.toString());
                    }
                    builder.escort2Tel(helper.getLinkmanTel());
                    builder.escort2HealthCode(helper.getHealthCode());
                    builder.escort2TravelCode(helper.getTravelCode());
                    builder.escort2HighRiskFlag(helper.getHighRiskFlag());
                    builder.escort2HighRiskArea(helper.getHighRiskArea());
                }
            }
            return builder.build();
        }).toList();
    }

    /**
     * 响应body
     */
    @Getter
    @Builder
    public static class QueryEscortRsp {
        /**
         * 入院时间
         */
        private LocalDateTime inDate;

        /**
         * 床号
         */
        private String bedNo;

        /**
         * 姓名
         */
        private String name;

        /**
         * 就诊卡号
         */
        private String cardNo;

        /**
         * 核酸结果
         */
        private String nucleicAcidResult;

        /**
         * 健康码
         */
        private HealthCodeEnum healthCode;

        /**
         * 行程码
         */
        private TravelCodeEnum travelCode;

        /**
         * 14天内是否去过高风险地区
         */
        @JsonAdapter(BooleanTypeAdapter_是否.class)
        private Boolean highRiskFlag;

        /**
         * 到过高风险地区清单
         */
        private String highRiskArea;

        /**
         * 陪护1姓名
         */
        private String escort1Name;

        /**
         * 陪护1就诊卡号
         */
        private String escort1CardNo;

        /**
         * 陪护1就诊卡号
         */
        private String escort1IdenNo;

        /**
         * 陪护1核酸结果
         */
        private String escort1NucleicAcidResult;

        /**
         * 陪护1联系电话
         */
        private String escort1Tel;

        /**
         * 陪护1健康码
         */
        private HealthCodeEnum escort1HealthCode;

        /**
         * 陪护1行程码
         */
        private TravelCodeEnum escort1TravelCode;

        /**
         * 陪护1高风险标识
         */
        @JsonAdapter(BooleanTypeAdapter_是否.class)
        private Boolean escort1HighRiskFlag;

        /**
         * 陪护1高风险地区
         */
        private String escort1HighRiskArea;

        /**
         * 陪护1姓名
         */
        private String escort2Name;

        /**
         * 陪护1就诊卡号
         */
        private String escort2CardNo;

        /**
         * 陪护1就诊卡号
         */
        private String escort2IdenNo;

        /**
         * 陪护1核酸结果
         */
        private String escort2NucleicAcidResult;

        /**
         * 陪护1联系电话
         */
        private String escort2Tel;

        /**
         * 陪护2健康码
         */
        private HealthCodeEnum escort2HealthCode;

        /**
         * 陪护2行程码
         */
        private TravelCodeEnum escort2TravelCode;

        /**
         * 陪护2高风险标识
         */
        @JsonAdapter(BooleanTypeAdapter_是否.class)
        private Boolean escort2HighRiskFlag;

        /**
         * 陪护2高风险地区
         */
        private String escort2HighRiskArea;
    }

    /**
     * 查询科室的患者及陪护基本信息
     * 
     * @param deptCode
     * @return
     */
    @ApiName("查询科室住院患者的陪护人信息")
    @RequestMapping(value = "queryData", method = RequestMethod.POST, produces = MediaType.JSON)
    RspWrapper<List<QueryData.RspBody>> queryData(@RequestBody @Valid QueryData.ReqBody reqBody) {
        try {
            // 查询指定科室的在院患者
            var pats = inMainInfoMapper.queryInMainInfos(
                    FinIprInMainInfoMapper.Key.builder()
                            .deptCode(reqBody.deptCode)
                            .states(Lists.newArrayList(InStateEnum.病房接诊)).build());

            // 过滤部分患者
            var filteredPats = pats.stream().filter(x -> {
                // 检索特殊标识
                var specialFlag = specialCityPatientCache.get(x.getInpatientNo());
                if (specialFlag != null) {
                    switch (Optional.fromNullable(specialFlag.getIsSpecial()).or("0")) {
                        case "0":
                            break;

                        default:
                            return false;
                    }
                }
                // 门诊医保
                if (Optional.fromNullable(x.getExtFlag2()).or("0").equals("4")) {
                    return false;
                }
                return true;
            }).toList();

            return RspWrapper.wrapSuccessResponse(filteredPats.stream().map(x -> {
                var builder = QueryData.RspBody.builder();
                // 患者信息
                builder.inDate(x.getInDate());
                builder.bedNo(bedNoTunnel.tunneling(x.getBedNo()));
                builder.name(x.getName());
                builder.cardNo(x.getCardNo());
                var patientInfo = patientInfoCache.get(x.getCardNo());
                if (patientInfo != null) {
                    builder.healthCode(patientInfo.getHealthCode());
                    builder.travelCode(patientInfo.getTravelCode());
                    builder.highRiskFlag(patientInfo.getHighRiskFlag());
                    builder.highRiskArea(patientInfo.getHighRiskArea());
                }
                var nats = natsTunnel.tunneling(NatsTunnel.Key.builder()
                        .cardNos(Lists.newArrayList(x.getCardNo(), x.getPatientNo()))
                        .duration(Duration.ofDays(14))
                        .build());
                if (nats != null) {
                    builder.nucleicAcidResult(nats.toString());
                }
                // 陪护信息
                var escortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                        .patientCardNo(x.getCardNo())
                        .states(Lists.newArrayList(
                                StateEnum.无核酸检测结果,
                                StateEnum.等待院内核酸检测结果,
                                StateEnum.等待院外核酸检测结果审核,
                                StateEnum.生效中,
                                StateEnum.其他))
                        .build());
                if (escortInfos.size() >= 1) {
                    var escort = escortInfos.get(0);
                    var helper = patientInfoCache.get(escort.getHelperCardNo());
                    if (helper != null) {
                        builder.escort1Name(helper.getName());
                        builder.escort1CardNo(helper.getCardNo());
                        builder.escort1IdenNo(helper.getIdentityCardNo());
                        var helperNats = natsTunnel.tunneling(NatsTunnel.Key.builder()
                                .cardNos(Lists.newArrayList(helper.getCardNo()))
                                .duration(Duration.ofDays(14))
                                .build());
                        if (helperNats != null) {
                            builder.escort1NucleicAcidResult(helperNats.toString());
                        }
                        builder.escort1Tel(helper.getLinkmanTel());
                        builder.escort1HealthCode(helper.getHealthCode());
                        builder.escort1TravelCode(helper.getTravelCode());
                        builder.escort1HighRiskFlag(helper.getHighRiskFlag());
                        builder.escort1HighRiskArea(helper.getHighRiskArea());
                    }
                }
                if (escortInfos.size() >= 2) {
                    var escort = escortInfos.get(1);
                    var helper = patientInfoCache.get(escort.getHelperCardNo());
                    if (helper != null) {
                        builder.escort2Name(helper.getName());
                        builder.escort2CardNo(helper.getCardNo());
                        builder.escort2IdenNo(helper.getIdentityCardNo());
                        var helperNats = natsTunnel.tunneling(NatsTunnel.Key.builder()
                                .cardNos(Lists.newArrayList(helper.getCardNo()))
                                .duration(Duration.ofDays(14))
                                .build());
                        if (helperNats != null) {
                            builder.escort2NucleicAcidResult(helperNats.toString());
                        }
                        builder.escort2Tel(helper.getLinkmanTel());
                        builder.escort2HealthCode(helper.getHealthCode());
                        builder.escort2TravelCode(helper.getTravelCode());
                        builder.escort2HighRiskFlag(helper.getHighRiskFlag());
                        builder.escort2HighRiskArea(helper.getHighRiskArea());
                    }
                }
                return builder.build();
            }).toList());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    static class QueryData {
        static class ReqBody {
            /**
             * 科室编码
             */
            String deptCode;
        }

        @Builder
        static class RspBody {
            /**
             * 入院时间
             */
            LocalDateTime inDate;

            /**
             * 床号
             */
            String bedNo;

            /**
             * 姓名
             */
            String name;

            /**
             * 就诊卡号
             */
            String cardNo;

            /**
             * 核酸结果
             */
            String nucleicAcidResult;

            /**
             * 健康码
             */
            HealthCodeEnum healthCode;

            /**
             * 行程码
             */
            TravelCodeEnum travelCode;

            /**
             * 14天内是否去过高风险地区
             */
            @JsonAdapter(BooleanTypeAdapter_是否.class)
            Boolean highRiskFlag;

            /**
             * 到过高风险地区清单
             */
            String highRiskArea;

            /**
             * 陪护1姓名
             */
            String escort1Name;

            /**
             * 陪护1就诊卡号
             */
            String escort1CardNo;

            /**
             * 陪护1就诊卡号
             */
            String escort1IdenNo;

            /**
             * 陪护1核酸结果
             */
            String escort1NucleicAcidResult;

            /**
             * 陪护1联系电话
             */
            String escort1Tel;

            /**
             * 陪护1健康码
             */
            HealthCodeEnum escort1HealthCode;

            /**
             * 陪护1行程码
             */
            TravelCodeEnum escort1TravelCode;

            /**
             * 陪护1高风险标识
             */
            @JsonAdapter(BooleanTypeAdapter_是否.class)
            Boolean escort1HighRiskFlag;

            /**
             * 陪护1高风险地区
             */
            String escort1HighRiskArea;

            /**
             * 陪护1姓名
             */
            String escort2Name;

            /**
             * 陪护1就诊卡号
             */
            String escort2CardNo;

            /**
             * 陪护1就诊卡号
             */
            String escort2IdenNo;

            /**
             * 陪护1核酸结果
             */
            String escort2NucleicAcidResult;

            /**
             * 陪护1联系电话
             */
            String escort2Tel;

            /**
             * 陪护2健康码
             */
            HealthCodeEnum escort2HealthCode;

            /**
             * 陪护2行程码
             */
            TravelCodeEnum escort2TravelCode;

            /**
             * 陪护2高风险标识
             */
            @JsonAdapter(BooleanTypeAdapter_是否.class)
            Boolean escort2HighRiskFlag;

            /**
             * 陪护2高风险地区
             */
            String escort2HighRiskArea;
        }
    }
}

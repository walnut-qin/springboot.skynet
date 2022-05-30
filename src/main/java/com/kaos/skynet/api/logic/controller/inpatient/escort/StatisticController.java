package com.kaos.skynet.api.logic.controller.inpatient.escort;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.gson.annotations.JsonAdapter;
import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexCheckCache;
import com.kaos.skynet.api.data.cache.pipe.lis.LisResultNewCache;
import com.kaos.skynet.api.data.converter.BedNoConverter;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.enums.common.HealthCodeEnum;
import com.kaos.skynet.api.enums.common.TravelCodeEnum;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.json.gson.adapter.bool.ChineseBooleanTypeAdapter;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping("/ms/inpatient/escort/statistic")
public class StatisticController {
    /**
     * 床号转缩略床号
     */
    @Autowired
    BedNoConverter bedNoConverter;

    /**
     * 缓存数据
     */
    @Autowired
    DataCache dataCache;

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
     * 核酸检测
     */
    final Converter<List<String>, String> nucleicConverter = new Converter<List<String>, String>() {
        @Override
        public String convert(List<String> cardNos) {
            // 构造结果集
            List<Pair<LocalDateTime, String>> result = Lists.newArrayList();
            for (var cardNo : cardNos) {
                // 检索院内核酸记录
                var r1 = dataCache.getLisResultCache().get(
                        LisResultNewCache.Key.builder()
                                .patientId(cardNo)
                                .itemCodes(Lists.newArrayList("SARS-CoV-2-RNA"))
                                .offset(14).build());
                if (!r1.isEmpty()) {
                    result.addAll(r1.stream().map(x -> {
                        return new Pair<LocalDateTime, String>(x.getInspectDate(), x.getResult());
                    }).toList());
                }
                // 检索院外核酸报告
                var r2 = dataCache.getAnnexCheckCache().getSlaveCache().get(
                        EscortAnnexCheckCache.SlaveCache.Key.builder()
                                .cardNo(cardNo)
                                .offset(14).build());
                if (!r2.isEmpty()) {
                    result.addAll(r2.stream().map(x -> {
                        return new Pair<LocalDateTime, String>(x.getInspectDate(), x.getNegative() ? "阴性(-)<o>" : "阳性");
                    }).toList());
                }
            }
            if (!result.isEmpty()) {
                // 按时间逆序
                result.sort((x, y) -> {
                    return y.getValue0().compareTo(x.getValue0());
                });
                return result.get(0).getValue1();
            }
            return null;
        };
    };

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
            var specialFlag = dataCache.getSpecialCityPatientCache().get(x.getInpatientNo());
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
            builder.bedNo(bedNoConverter.convert(x.getBedNo()));
            builder.name(x.getName());
            builder.cardNo(x.getCardNo());
            var patientInfo = dataCache.getPatientInfoCache().get(x.getCardNo());
            if (patientInfo != null) {
                builder.healthCode(patientInfo.getHealthCode());
                builder.travelCode(patientInfo.getTravelCode());
                builder.highRiskFlag(patientInfo.getHighRiskFlag());
                builder.highRiskArea(patientInfo.getHighRiskArea());
            }
            builder.nucleicAcidResult(nucleicConverter.convert(Lists.newArrayList(x.getCardNo(), x.getPatientNo())));
            // 陪护信息
            var escortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                    .patientCardNo(x.getCardNo())
                    .states(Lists.newArrayList(
                            EscortStateEnum.无核酸检测结果,
                            EscortStateEnum.等待院内核酸检测结果,
                            EscortStateEnum.等待院外核酸检测结果审核,
                            EscortStateEnum.生效中,
                            EscortStateEnum.其他))
                    .build());
            if (escortInfos.size() >= 1) {
                var escort = escortInfos.get(0);
                var helper = dataCache.getPatientInfoCache().get(escort.getHelperCardNo());
                if (helper != null) {
                    builder.escort1Name(helper.getName());
                    builder.escort1CardNo(helper.getCardNo());
                    builder.escort1IdenNo(helper.getIdentityCardNo());
                    builder.escort1NucleicAcidResult(nucleicConverter.convert(Lists.newArrayList(helper.getCardNo())));
                    builder.escort1Tel(helper.getLinkmanTel());
                    builder.escort1HealthCode(helper.getHealthCode());
                    builder.escort1TravelCode(helper.getTravelCode());
                    builder.escort1HighRiskFlag(helper.getHighRiskFlag());
                    builder.escort1HighRiskArea(helper.getHighRiskArea());
                }
            }
            if (escortInfos.size() >= 2) {
                var escort = escortInfos.get(1);
                var helper = dataCache.getPatientInfoCache().get(escort.getHelperCardNo());
                if (helper != null) {
                    builder.escort2Name(helper.getName());
                    builder.escort2CardNo(helper.getCardNo());
                    builder.escort2IdenNo(helper.getIdentityCardNo());
                    builder.escort2NucleicAcidResult(nucleicConverter.convert(Lists.newArrayList(helper.getCardNo())));
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
        @JsonAdapter(ChineseBooleanTypeAdapter.class)
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
        @JsonAdapter(ChineseBooleanTypeAdapter.class)
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
        @JsonAdapter(ChineseBooleanTypeAdapter.class)
        private Boolean escort2HighRiskFlag;

        /**
         * 陪护2高风险地区
         */
        private String escort2HighRiskArea;
    }
}

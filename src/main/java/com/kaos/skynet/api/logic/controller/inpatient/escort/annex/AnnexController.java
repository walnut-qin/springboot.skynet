package com.kaos.skynet.api.logic.controller.inpatient.escort.annex;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.data.cache.inpatient.escort.annex.EscortAnnexInfoCache;
import com.kaos.skynet.api.data.converter.PatientNameConverter;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.annex.EscortAnnexInfo;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.enums.inpatient.InStateEnum;
import com.kaos.skynet.api.enums.inpatient.escort.EscortStateEnum;
import com.kaos.skynet.api.logic.controller.inpatient.escort.EscortLock;
import com.kaos.skynet.api.logic.service.inpatient.escort.EscortService;
import com.kaos.skynet.api.logic.service.inpatient.escort.annex.AnnexService;
import com.kaos.skynet.core.Locks;
import com.kaos.skynet.core.type.converter.string.bool.NumericStringToBooleanConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;
import java.net.URL;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnails;

@Log4j
@Validated
@RestController
@RequestMapping({ "/ms/inpatient/escort/annex", "/ms/inpatient/escort" })
public class AnnexController {
    @Autowired
    PatientNameConverter patientNameConverter;

    /**
     * 转换器
     */
    @Autowired
    NumericStringToBooleanConverter stringToBooleanConverter;

    /**
     * 缓存
     */
    @Autowired
    DataCache dataCache;

    /**
     * 锁
     */
    @Autowired
    EscortLock escortLock;

    @Autowired
    AnnexService annexService;

    @Autowired
    EscortService escortService;

    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    @RequestMapping(value = "uploadAnnex", method = RequestMethod.GET, produces = MediaType.TEXT)
    public String uploadAnnex(@NotNull(message = "陪护人卡号不能为空") String helperCardNo,
            @NotNull(message = "附件链接不能为空") String url) {
        // 入参记录
        log.info(String.format("上传附件<helperCardNo = %s, url = %s>", helperCardNo, url));

        // 调用服务 - 不存在冲突，无需加锁
        var annexNo = annexService.uploadAnnex(helperCardNo, url);

        // 查询所有关联陪护证
        var escorts = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .helperCardNo(helperCardNo)
                .states(Lists.newArrayList(
                        EscortStateEnum.无核酸检测结果,
                        EscortStateEnum.等待院内核酸检测结果,
                        EscortStateEnum.等待院外核酸检测结果审核,
                        EscortStateEnum.生效中,
                        EscortStateEnum.其他))
                .build());
        if (escorts != null && !escorts.isEmpty()) {
            for (var escort : escorts) {
                // 更新关联陪护状态
                Locks.newLockExecutor().link(escortLock.getStateLock().getLock(escort.getEscortNo())).execute(() -> {
                    escortService.updateState(escort.getEscortNo(), null, "WebService", "患者上传外院报告");
                });
            }
        }

        return annexNo;
    }

    @RequestMapping(value = "checkAnnex", method = RequestMethod.GET, produces = MediaType.TEXT)
    public void checkAnnex(@NotNull(message = "附件号不能为空") String annexNo,
            @NotNull(message = "审核人不能为空") String checker,
            @NotNull(message = "审核结果不能为空") String negativeFlag,
            @NotNull(message = "检验时间不能为空") LocalDateTime inspectDate) {
        // 转换适配
        Boolean negativeFlagBoolean = stringToBooleanConverter.convert(negativeFlag);

        // 入参记录
        log.info(String.format("审核附件<annexNo = %s, checker = %s, negativeFlag = %s, inspectDate = %s>", annexNo,
                checker, negativeFlag.toString(), inspectDate.toString()));

        // 加状态操作锁，防止同时操作同一个陪护证
        Locks.newLockExecutor().link(escortLock.getAnnexLock().getLock(annexNo)).execute(() -> {
            annexService.checkAnnex(annexNo, checker, negativeFlagBoolean, inspectDate);
        });

        // 检索附件信息
        var annex = dataCache.getAnnexInfoCache().getMasterCache().get(annexNo);

        // 查询所有关联陪护证
        var escorts = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .helperCardNo(annex.getCardNo())
                .states(Lists.newArrayList(
                        EscortStateEnum.无核酸检测结果,
                        EscortStateEnum.等待院内核酸检测结果,
                        EscortStateEnum.等待院外核酸检测结果审核,
                        EscortStateEnum.生效中,
                        EscortStateEnum.其他))
                .build());
        if (escorts != null && !escorts.isEmpty()) {
            for (var escort : escorts) {
                // 更新关联陪护状态
                Locks.newLockExecutor().link(escortLock.getStateLock().getLock(escort.getEscortNo())).execute(() -> {
                    escortService.updateState(escort.getEscortNo(), null, "WebService", "患者上传外院报告");
                });
            }
        }
    }

    @RequestMapping(value = "queryAnnexInDept", method = RequestMethod.GET, produces = MediaType.JSON)
    public List<QueryAnnexInDeptRsp> queryAnnexInDept(@NotNull(message = "科室编码不能为空") String deptCode,
            @NotNull(message = "审核标识不能为空") String checked) {
        // 转换适配
        Boolean checkedBoolean = stringToBooleanConverter.convert(checked);

        // 入参记录
        log.info(String.format("查询科室信息<deptCode = %s, checked = %s>", deptCode, checked.toString()));

        // 检索该科室所有患者
        var inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                .deptCode(deptCode)
                .states(Lists.newArrayList(InStateEnum.病房接诊))
                .build());

        // 查询所有有效的陪护人
        var escortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNos(inMainInfos.stream().map(x -> {
                    return x.getCardNo();
                }).toList())
                .states(Lists.newArrayList(EscortStateEnum.无核酸检测结果,
                        EscortStateEnum.等待院内核酸检测结果,
                        EscortStateEnum.等待院外核酸检测结果审核,
                        EscortStateEnum.生效中,
                        EscortStateEnum.其他))
                .build());

        // 轮询陪护记录
        Multimap<String, String> escortRelation = ArrayListMultimap.create();
        for (EscortMainInfo escortMainInfo : escortInfos) {
            // 登记陪护关系
            escortRelation.put(escortMainInfo.getHelperCardNo(), escortMainInfo.getPatientCardNo());
        }

        // 构造所有附件信息
        List<EscortAnnexInfo> annexInfos = Lists.newArrayList();
        for (String helperCardNo : escortRelation.keySet()) {
            annexInfos.addAll(
                    dataCache.getAnnexInfoCache().getSlaveCache().get(
                            EscortAnnexInfoCache.SlaveCache.Key.builder()
                                    .cardNo(helperCardNo)
                                    .checked(checkedBoolean).build()));
        }

        // 映射到响应体
        List<QueryAnnexInDeptRsp> rsps = annexInfos.stream().map(x -> {
            var builder = QueryAnnexInDeptRsp.builder();
            builder.annexNo(x.getAnnexNo());
            builder.helperName(patientNameConverter.convert(x.getCardNo()));
            builder.picUrl(x.getAnnexUrl());
            builder.patientNames(Set.copyOf(escortRelation.get(x.getCardNo())).stream().map(y -> {
                return patientNameConverter.convert(y);
            }).toList());
            // 若查询审核内容，则添加审核信息
            if (checkedBoolean) {
                builder.negative(dataCache.getAnnexCheckCache().getMasterCache().get(x.getAnnexNo()).getNegative());
                builder.inspectDate(
                        dataCache.getAnnexCheckCache().getMasterCache().get(x.getAnnexNo()).getInspectDate());
            }
            return builder.build();
        }).toList();

        return rsps;
    }

    /**
     * 查询指定科室的附件信息body
     */
    @Getter
    @Builder
    public static class QueryAnnexInDeptRsp {
        /**
         * 附件编码
         */
        private String annexNo;

        /**
         * 陪护人姓名
         */
        private String helperName;

        /**
         * 附件链接
         */
        private String picUrl;

        /**
         * 被陪护患者列表
         */
        private List<String> patientNames;

        /**
         * 审核结果
         */
        private Boolean negative;

        /**
         * 检验日期
         */
        private LocalDateTime inspectDate;
    }

    @RequestMapping(value = "getPic", method = RequestMethod.GET, produces = MediaType.JPEG)
    public BufferedImage getPic(@NotNull(message = "键值不能为空") String refer) {
        // 根据refer号获取annexUrl
        var rec = dataCache.getAnnexInfoCache().getMasterCache().get(refer);
        if (rec == null) {
            throw new RuntimeException("未查询到附件记录");
        }

        // 读取图片并返回
        try {
            return Thumbnails.of(new URL(rec.getAnnexUrl())).scale(1.0f).asBufferedImage();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
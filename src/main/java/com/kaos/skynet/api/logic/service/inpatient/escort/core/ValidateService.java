package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.his.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.data.his.mapper.inpatient.order.MetOrdiOrderMapper;
import com.kaos.skynet.core.type.converter.DurationToStringConverter;
import com.kaos.skynet.core.type.utils.DurationUtils;
import com.kaos.skynet.core.type.utils.IntegerUtils;
import com.kaos.skynet.core.type.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.extern.log4j.Log4j;

/**
 * 有效性检查业务
 */
@Log4j
@Validated
@Service
public class ValidateService {
    /**
     * 患者信息
     */
    @Autowired
    ComPatientInfoCache patientInfoCache;

    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护证状态表接口
     */
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    /**
     * 住院实体接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 住院证实体接口
     */
    @Autowired
    FinIprPrepayInMapper prepayInMapper;

    /**
     * 住院医嘱接口
     */
    @Autowired
    MetOrdiOrderMapper metOrdiOrderMapper;

    /**
     * 时段转换器
     */
    DurationToStringConverter durationToStringConverter = new DurationToStringConverter("天", "小时", "分", "秒", true);

    /**
     * 判断是否可以注册
     * 
     * @param patientCardNo
     * @param helperCardNo
     * @param escape
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getHappenNo(@NotBlank String patientCardNo, @NotBlank String helperCardNo, @NotNull Boolean escape)
            throws RuntimeException {
        // 自陪护检查
        if (StringUtils.equals(patientCardNo, helperCardNo)) {
            log.error(String.format("不能给自己陪护(%s)", patientCardNo));
            throw new RuntimeException("不能给自己陪护");
        }

        // 检查患者是否存在
        var patient = patientInfoCache.get(patientCardNo);
        if (patient == null) {
            log.error(String.format("无效的患者(%s)", patientCardNo));
            throw new RuntimeException("无效的患者");
        }

        // 检查陪护人是否存在
        var helper = patientInfoCache.get(helperCardNo);
        if (helper == null) {
            log.error(String.format("无效的陪护人(%s)", helperCardNo));
            throw new RuntimeException("无效的陪护人");
        }

        // 检索旧陪护证
        var oldEscortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNo(patientCardNo)
                .helperCardNo(helperCardNo)
                .states(Lists.newArrayList(
                        EscortStateRec.StateEnum.无核酸检测结果,
                        EscortStateRec.StateEnum.等待院内核酸检测结果,
                        EscortStateRec.StateEnum.等待院外核酸检测结果审核,
                        EscortStateRec.StateEnum.生效中,
                        EscortStateRec.StateEnum.其他))
                .build());
        if (!oldEscortInfos.isEmpty()) {
            log.error(String.format("陪护关系已存在"));
            throw new RuntimeException("陪护关系已存在");
        }

        // 可逃逸判断
        if (!escape) {
            escapableCheck(patientCardNo, helperCardNo);
        }

        // 陪护人陪护上限
        var helperEscortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .helperCardNo(helperCardNo)
                .states(Lists.newArrayList(
                        EscortStateRec.StateEnum.无核酸检测结果,
                        EscortStateRec.StateEnum.等待院内核酸检测结果,
                        EscortStateRec.StateEnum.等待院外核酸检测结果审核,
                        EscortStateRec.StateEnum.生效中,
                        EscortStateRec.StateEnum.其他))
                .build());
        if (helperEscortInfos.size() >= 2) {
            log.error(String.format("陪护人的陪护证已达到上限(%d)", helperEscortInfos.size()));
            throw new RuntimeException("陪护人的陪护证已达到上限");
        }

        // 患者陪护上限
        var patientEscortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNo(patientCardNo)
                .states(Lists.newArrayList(
                        EscortStateRec.StateEnum.无核酸检测结果,
                        EscortStateRec.StateEnum.等待院内核酸检测结果,
                        EscortStateRec.StateEnum.等待院外核酸检测结果审核,
                        EscortStateRec.StateEnum.生效中,
                        EscortStateRec.StateEnum.其他))
                .build());
        if (patientEscortInfos.size() >= 2) {
            log.error(String.format("患者的陪护证数量达到上限(%d)", helperEscortInfos.size()));
            throw new RuntimeException("患者的陪护证数量达到上限");
        }

        // 检索患者在院记录
        var inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                .cardNo(patientCardNo)
                .states(Lists.newArrayList(
                        FinIprInMainInfo.InStateEnum.住院登记,
                        FinIprInMainInfo.InStateEnum.病房接诊))
                .build());
        switch (inMainInfos.size()) {
            case 0 -> {
                return weakEscortCheck(patientEscortInfos.size(), patientCardNo);
            }

            case 1 -> {
                return strongEscortCheck(patientEscortInfos.size(), inMainInfos.get(0));
            }

            default -> {
                log.error(String.format("患者存在%d条在院记录, 无法创建有效关联", inMainInfos.size()));
                throw new RuntimeException("患者存在多条在院记录, 无法创建有效关联");
            }
        }
    }

    /**
     * 可逃逸判断
     * 
     * @param patientCardNo
     * @param helperCardNo
     */
    private void escapableCheck(String patientCardNo, String helperCardNo) {
        // 旧的已注销的陪护证判断
        var canceledEscortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNo(patientCardNo)
                .helperCardNo(helperCardNo)
                .states(Lists.newArrayList(EscortStateRec.StateEnum.注销))
                .build());
        if (canceledEscortInfos.isEmpty()) {
            return;
        }

        // 检索最近注销的时间
        LocalDateTime cancelDate = null;
        for (var escortInfo : canceledEscortInfos) {
            var lastState = escortStateRecMapper.queryLastEscortStateRec(escortInfo.getEscortNo());
            if (cancelDate == null) {
                cancelDate = lastState.getRecDate();
            }
            if (lastState.getRecDate().isAfter(cancelDate)) {
                cancelDate = lastState.getRecDate();
            }
        }

        // 注销后12小时内无法再次注册
        Duration duration = Duration.between(cancelDate, LocalDateTime.now());
        if (DurationUtils.compare(duration, Duration.ofHours(12)) < 0) {
            var offset = Duration.ofHours(12).minus(duration);
            var offsetStr = durationToStringConverter.convert(offset);
            log.error(String.format("注销12小时内无法再次注册, 剩余%s", offsetStr));
            throw new RuntimeException(String.format("注销12小时内无法再次注册, 剩余%s", offsetStr));
        }
    }

    /**
     * 弱陪护检查
     * 
     * @param patientCardNo
     * @return
     */
    private Integer weakEscortCheck(Integer escortCnt, String patientCardNo) {
        // 预住院仅能添加一个陪护
        switch (escortCnt) {
            case 0 -> {
                break;
            }

            default -> {
                log.error(String.format("患者(%s)没有开立第二陪护医嘱", "ZY01".concat(patientCardNo)));
                throw new RuntimeException("患者没有开立第二陪护医嘱");
            }
        }

        // 检索患者住院证
        var prepayIns = prepayInMapper.queryPrepayIns(FinIprPrepayInMapper.Key.builder()
                .cardNo(patientCardNo)
                .build());
        if (prepayIns.isEmpty()) {
            log.error("无可关联的住院证");
            throw new RuntimeException("无可关联的住院证");
        }

        // 最后一张住院证状态
        prepayIns.sort((x, y) -> {
            return IntegerUtils.compare(y.getHappenNo(), x.getHappenNo());
        });
        switch (prepayIns.get(0).getState()) {
            case 作废 -> {
                log.error("无可关联的住院证");
                throw new RuntimeException("无可关联的住院证");
            }

            default -> {
                return prepayIns.get(0).getHappenNo();
            }
        }
    }

    /**
     * 强陪护逻辑
     * 
     * @param escortCnt
     * @param inMainInfo
     * @return
     */
    private Integer strongEscortCheck(Integer escortCnt, FinIprInMainInfo inMainInfo) {
        switch (escortCnt) {
            case 0 -> {
                if (inMainInfo.getHappenNo() == null) {
                    log.error(String.format("患者未通过住院证入院(inpatientNo = %s)", inMainInfo.getInpatientNo()));
                    throw new RuntimeException("患者未通过住院证入院");
                }
                return inMainInfo.getHappenNo();
            }

            case 1 -> {
                // 读取患者第二陪护医嘱
                var orders = metOrdiOrderMapper.queryOrders(MetOrdiOrderMapper.Key.builder()
                        .inpatientNo(inMainInfo.getInpatientNo())
                        .termId("5070672")
                        .build());
                if (orders.isEmpty()) {
                    log.error(String.format("患者(%s)没有开立第二陪护医嘱", inMainInfo.getInpatientNo()));
                    throw new RuntimeException("患者没有开立第二陪护医嘱");
                } else {
                    return inMainInfo.getHappenNo();
                }
            }

            default -> {
                log.error("陪护逻辑异常");
                throw new RuntimeException("业务逻辑异常");
            }
        }
    }
}

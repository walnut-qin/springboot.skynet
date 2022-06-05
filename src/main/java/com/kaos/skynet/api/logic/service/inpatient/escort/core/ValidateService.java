package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import javax.validation.constraints.NotBlank;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.cache.common.ComPatientInfoCache;
import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprPrepayInMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.order.MetOrdiOrderMapper;
import com.kaos.skynet.core.type.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.Builder;
import lombok.Getter;
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
     * 判断是否可以注册
     * 
     * @param patientCardNo
     * @param helperCardNo
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer getHappenNo(@NotBlank String patientCardNo, @NotBlank String helperCardNo) {
        // 自陪护检查
        if (StringUtils.equals(patientCardNo, helperCardNo)) {
            log.error(String.format("不能给自己陪护(%s)", patientCardNo));
            throw new RuntimeException("不能给自己陪护");
        }

        // 检索患者信息
        var patient = patientInfoCache.get(patientCardNo);
        if (patient == null) {
            log.error(String.format("无效的患者(%s)", patientCardNo));
            throw new RuntimeException("无效的患者");
        }

        // 检索陪护人信息
        var helper = patientInfoCache.get(helperCardNo);
        if (helper == null) {
            log.error(String.format("无效的陪护人(%s)", helperCardNo));
            throw new RuntimeException("无效的陪护人");
        }

        // 检索陪护人关联的陪护证
        var helperEscortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .helperCardNo(helperCardNo)
                .states(Lists.newArrayList(
                        EscortStateRec.StateEnum.无核酸检测结果,
                        EscortStateRec.StateEnum.等待院内核酸检测结果,
                        EscortStateRec.StateEnum.等待院外核酸检测结果审核,
                        EscortStateRec.StateEnum.生效中,
                        EscortStateRec.StateEnum.其他))
                .build());
        if (helperEscortInfos.size() > 2) {
            log.error(String.format("陪护人的陪护证已达到上限(%s)", helperEscortInfos.size()));
            throw new RuntimeException("陪护人的陪护证已达到上限");
        }

        // 检索患者陪护证
        var patientEscortInfos = escortMainInfoMapper.queryEscortMainInfos(EscortMainInfoMapper.Key.builder()
                .patientCardNo(helperCardNo)
                .states(Lists.newArrayList(
                        EscortStateRec.StateEnum.无核酸检测结果,
                        EscortStateRec.StateEnum.等待院内核酸检测结果,
                        EscortStateRec.StateEnum.等待院外核酸检测结果审核,
                        EscortStateRec.StateEnum.生效中,
                        EscortStateRec.StateEnum.其他))
                .build());
        switch (patientEscortInfos.size()) {
            case 0 -> {
            }

            case 1 -> {
                // 检索患者在院记录
                var inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                        .cardNo(patientCardNo)
                        .states(Lists.newArrayList(
                                FinIprInMainInfo.InStateEnum.住院登记,
                                FinIprInMainInfo.InStateEnum.病房接诊))
                        .build());
                switch (inMainInfos.size()) {
                    case 0 -> {
                        break;
                    }

                    case 1 -> {
                        // 获取第二陪护医嘱
                        metOrdiOrderMapper.queryOrders(MetOrdiOrderMapper.Key.builder()
                                .termId("")
                                .build());
                    }

                    default -> {
                        log.error(String.format("患者存在%d条在院记录, 无法创建有效关联", inMainInfos.size()));
                        throw new RuntimeException("患者存在多条在院记录, 无法创建有效关联");
                    }
                }
            }

            default -> {
                log.error(String.format("患者的陪护证数量达到上限(%s)", helperEscortInfos.size()));
                throw new RuntimeException("患者的陪护证数量达到上限");
            }
        }
        return null;
    }

    @Getter
    @Builder
    public static class Result {
        /**
         * 可以注册陪护
         */
        private Boolean canRegister;

        /**
         * 若失败，保存原因
         */
        private String reason;

        /**
         * 若成功，保存关联的happenNo
         */
        private Integer happenNo;

        /**
         * 若成功，保存最新的状态
         */
        private StateEnum state;
    }
}

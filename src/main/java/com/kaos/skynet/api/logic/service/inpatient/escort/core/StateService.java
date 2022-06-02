package com.kaos.skynet.api.logic.service.inpatient.escort.core;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.entity.inpatient.FinIprInMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.mapper.inpatient.FinIprInMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortStateRecMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;

/**
 * 陪护证状态服务
 */
@Log4j
@Validated
@Service
public class StateService {
    /**
     * 陪护证主表接口
     */
    @Autowired
    EscortMainInfoMapper escortMainInfoMapper;

    /**
     * 陪护证状态接口
     */
    @Autowired
    EscortStateRecMapper escortStateRecMapper;

    /**
     * 住院实体接口
     */
    @Autowired
    FinIprInMainInfoMapper inMainInfoMapper;

    /**
     * 查询陪护证状态
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Result queryEscortState(@NotBlank(message = "陪护证号不能为空") String escortNo) {
        // 检索陪护证主表
        EscortMainInfo escortInfo = escortMainInfoMapper.queryEscortMainInfo(escortNo);
        if (escortInfo == null) {
            String errInfo = String.format("陪护证(%s)不存在", escortNo);
            log.error(errInfo);
            throw new RuntimeException(errInfo);
        }

        // 检索住院实体
        List<FinIprInMainInfo> inMainInfos = inMainInfoMapper.queryInMainInfos(FinIprInMainInfoMapper.Key.builder()
                .cardNo(escortInfo.getPatientCardNo())
                .happenNo(escortInfo.getHappenNo())
                .build());
        if (!inMainInfos.isEmpty()) {
            // 强陪护确定

        } else { // 弱陪护确定
        }

        return null;
    }

    /**
     * 状态结果集
     */
    @Getter
    @AllArgsConstructor
    public static class Result {
        /**
         * 状态
         */
        private StateEnum state;

        /**
         * 判断原因
         */
        private String reason;
    }
}

package com.kaos.skynet.api.logic.service.inpatient.escort;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.kaos.skynet.api.data.cache.inpatient.escort.EscortStateRecCache;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortVip;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec.ActionEnum;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec.StateEnum;
import com.kaos.skynet.api.data.mapper.common.SequenceMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortActionRecMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortMainInfoMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortStateRecMapper;
import com.kaos.skynet.api.data.mapper.inpatient.escort.EscortVipMapper;
import com.kaos.skynet.api.logic.service.inpatient.escort.core.StateService;
import com.kaos.skynet.api.logic.service.inpatient.escort.core.ValidateService;
import com.kaos.skynet.core.type.utils.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class EscortService {
    /**
     * 核心鉴权业务
     */
    @Autowired
    ValidateService validateService;

    /**
     * 核心状态业务
     */
    @Autowired
    StateService stateService;

    /**
     * 序列获取器
     */
    @Autowired
    SequenceMapper sequenceMapper;

    /**
     * VIP表接口
     */
    @Autowired
    EscortVipMapper vipMapper;

    /**
     * 主表接口
     */
    @Autowired
    EscortMainInfoMapper mainInfoMapper;

    /**
     * 状态表接口
     */
    @Autowired
    EscortStateRecMapper stateRecMapper;

    /**
     * 状态表接口
     */
    @Autowired
    EscortActionRecMapper actionRecMapper;

    /**
     * 陪护状态缓存
     */
    @Autowired
    EscortStateRecCache escortStateRecCache;

    /**
     * 登记陪护证
     * 
     * @param patientCardNo 患者卡号
     * @param helperCardNo  陪护人卡号
     * @param operCode      操作员
     * @param escape        逃逸码, 逃逸部分校验
     * @return
     */
    @Transactional
    public String register(String patientCardNo, String helperCardNo, String operCode, @NotNull Boolean escape) {
        // 获取待注册的happenNo
        Integer happenNo = validateService.getHappenNo(patientCardNo, helperCardNo, escape);
        if (happenNo == null) {
            throw new RuntimeException("获取关联happenNo失败");
        }

        // 构造模拟陪护实体
        var builder = EscortMainInfo.builder();
        builder.patientCardNo(patientCardNo);
        builder.happenNo(happenNo);
        builder.helperCardNo(helperCardNo);

        // 陪护状态检查
        var stateResult = stateService.queryEscortState(builder.build());
        if (stateResult.getState() == EscortStateRec.StateEnum.注销) {
            log.error(stateResult.getReason());
            throw new RuntimeException(stateResult.getReason());
        }

        // 生成陪护证号
        String escortNo = StringUtils.leftPad(sequenceMapper.query("KAOS.SEQ_ESCORT_NO"), 10, '0');

        // 登记VIP
        var vip = vipMapper.queryEscortVip(patientCardNo, happenNo);
        if (vip == null) {
            var vipBuilder = EscortVip.builder();
            vipBuilder.patientCardNo(patientCardNo);
            vipBuilder.happenNo(happenNo);
            vipBuilder.helperCardNo(helperCardNo);
            vipBuilder.recDate(LocalDateTime.now());
            vipMapper.insertEscortVip(vipBuilder.build());
        }

        // 更新陪护主表
        mainInfoMapper.insertEscortMainInfo(builder.escortNo(escortNo).build());

        // 插入状态表
        var stateBuilder = EscortStateRec.builder();
        stateBuilder.escortNo(escortNo);
        stateBuilder.recNo(1);
        stateBuilder.state(stateResult.getState());
        stateBuilder.recEmplCode(operCode);
        stateBuilder.recDate(LocalDateTime.now());
        stateBuilder.remark(stateResult.getReason());
        stateRecMapper.insertEscortStateRec(stateBuilder.build());

        return escortNo;
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo 陪护证编号
     * @param state    状态
     * @param operCode 状态
     * @param remark   状态
     */
    @Transactional
    public void updateState(String escortNo, StateEnum state, String operCode, String remark) {
        // 若未设置状态，则获取实时状态
        if (state == null) {
            var stateResult = stateService.queryEscortState(escortNo);
            state = stateResult.getState();
            remark = stateResult.getReason();
        }

        // 读取状态列表
        var lastState = stateRecMapper.queryLastEscortStateRec(escortNo);
        if (lastState == null) {
            stateRecMapper.insertEscortStateRec(EscortStateRec.builder()
                    .escortNo(escortNo)
                    .recNo(1)
                    .state(state)
                    .recEmplCode(operCode)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        } else if (lastState.getState() != state) {
            stateRecMapper.insertEscortStateRec(EscortStateRec.builder()
                    .escortNo(escortNo)
                    .recNo(lastState.getRecNo() + 1)
                    .state(state)
                    .recEmplCode(operCode)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        }

        // 更新缓存
        escortStateRecCache.refresh(escortNo);
    }

    /**
     * 登记陪护行为
     * 
     * @param escortNo 陪护证编号
     * @param action   状态
     * @param remark   备注信息
     */
    @Transactional
    public void recordAction(String escortNo, ActionEnum action, String remark) {
        // 检索陪护实体
        var mainInfo = mainInfoMapper.queryEscortMainInfo(escortNo);
        if (mainInfo == null) {
            throw new RuntimeException(String.format("陪护证不存在(%s)", escortNo));
        }

        // 检索最后一个行为
        var actionRecs = actionRecMapper.queryActions(escortNo);
        if (actionRecs == null || actionRecs.isEmpty()) {
            actionRecMapper.insertAction(EscortActionRec.builder()
                    .escortNo(escortNo)
                    .recNo(1)
                    .action(action)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        } else {
            actionRecMapper.insertAction(EscortActionRec.builder()
                    .escortNo(escortNo)
                    .recNo(actionRecs.get(0).getRecNo() + 1)
                    .action(action)
                    .recDate(LocalDateTime.now())
                    .remark(remark).build());
        }
    }
}

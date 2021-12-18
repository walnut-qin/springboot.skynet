package com.kaos.his.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.kaos.his.controller.escort.QueryEscortStateController.EscortState;
import com.kaos.his.entity.credential.AnnexCheckInfo;
import com.kaos.his.entity.credential.AnnexInfo;
import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.credential.EscortCardAction;
import com.kaos.his.entity.credential.EscortCardState;
import com.kaos.his.entity.credential.PreinCard;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.PreinCardStateEnum;
import com.kaos.his.mapper.config.VariableMapper;
import com.kaos.his.mapper.credential.AnnexCheckInfoMapper;
import com.kaos.his.mapper.credential.AnnexInfoMapper;
import com.kaos.his.mapper.credential.EscortCardActionMapper;
import com.kaos.his.mapper.credential.EscortCardMapper;
import com.kaos.his.mapper.credential.EscortCardStateMapper;
import com.kaos.his.mapper.credential.PreinCardMapper;
import com.kaos.his.mapper.lis.NucleicAcidTestMapper;
import com.kaos.his.mapper.order.InpatientOrderMapper;
import com.kaos.his.mapper.order.OutpatientOrderMapper;
import com.kaos.his.mapper.organization.DepartmentMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;
import com.kaos.his.mapper.personnel.PatientMapper;
import com.kaos.util.ListHelper;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 陪护证系统的接口
 */
@Service
public class EscortService {
    /**
     * 陪护人实体接口
     */
    @Autowired
    EscortCardMapper escortMapper;

    /**
     * 陪护状态接口
     */
    @Autowired
    EscortCardStateMapper escortCardStateMapper;

    /**
     * 陪护动作接口
     */
    @Autowired
    EscortCardActionMapper escortCardActionMapper;

    /**
     * 陪护附件接口
     */
    @Autowired
    AnnexInfoMapper annexInfoMapper;

    /**
     * 附件审核接口
     */
    @Autowired
    AnnexCheckInfoMapper annexCheckInfoMapper;

    /**
     * 住院证实体接口
     */
    @Autowired
    PreinCardMapper preinCardMapper;

    /**
     * 住院患者实体接口
     */
    @Autowired
    InpatientMapper inpatientMapper;

    /**
     * 患者实体接口
     */
    @Autowired
    PatientMapper patientMapper;

    /**
     * 核酸检测结果读取接口
     */
    @Autowired
    NucleicAcidTestMapper nucleicAcidTestMapper;

    /**
     * 门诊医嘱读取接口
     */
    @Autowired
    OutpatientOrderMapper outpatientOrderMapper;

    /**
     * 住院医嘱读取接口
     */
    @Autowired
    InpatientOrderMapper inpatientOrderMapper;

    /**
     * 科室信息接口
     */
    @Autowired
    DepartmentMapper departmentMapper;

    /**
     * 配置变量接口
     */
    @Autowired
    VariableMapper variableMapper;

    /**
     * 核心函数：判断陪护证的实际状态
     * 
     * @param escortCard
     * @return
     */
    private EscortStateEnum JudgeRealState(EscortCard escortCard) {
        // 入参判断
        if (escortCard == null) {
            return null;
        }

        // 如果尚未获取状态列表，则获取数据库当前记录的状态列表
        if (escortCard.states == null) {
            escortCard.states = this.escortCardStateMapper.QueryEscortCardStates(escortCard.escortNo);
        }

        // 辅助变量，时间锚点
        var cal = Calendar.getInstance();
        var beginDate = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, -7);
        var endDate = cal.getTime();

        // 预判断：如果该陪护证已注销，则其真实状态就是注销
        if (escortCard == null || ListHelper.GetLast(escortCard.states).state == EscortStateEnum.注销) {
            return EscortStateEnum.注销;
        }

        // 优先级0：如果陪护证关联的住院证不是患者的最后一张住院证，表示患者已经出院了，陪护证应当注销
        var latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(escortCard.patientCardNo);
        if (latestPreinCard == null || !latestPreinCard.happenNo.equals(escortCard.happenNo)) {
            return EscortStateEnum.注销;
        }

        // 优先级1：若陪护人7日内院内核酸结果为阴性，则生效陪护证
        var nats = this.nucleicAcidTestMapper.QueryNucleicAcidTest(escortCard.helperCardNo, "SARS-CoV-2-RNA", 7);
        if (nats != null && !nats.isEmpty() && ListHelper.GetLast(nats).negative) {
            return EscortStateEnum.生效中;
        }

        // 优先级2：若陪护人已审核的院外报告中，最近的一个结果为阴性，则生效陪护证
        var rpts = this.annexCheckInfoMapper.QueryWithExecDateLimit(escortCard.helperCardNo, beginDate, endDate);
        if (rpts != null && !rpts.isEmpty() && ListHelper.GetLast(rpts).negative) {
            return EscortStateEnum.生效中;
        }

        // 优先级3：判断院内核酸医嘱
        var ordRs = this.outpatientOrderMapper.QueryOutpatientOrders(escortCard.helperCardNo, "438771", 7);
        if (ordRs != null && !ordRs.isEmpty()) {
            // 有院内医嘱
            return EscortStateEnum.等待院内核酸检测结果;
        }

        // 优先级4：判断有无院外待审核结果
        var unchecked = this.annexInfoMapper.QueryUncheckedAnnexInfos(escortCard.helperCardNo, beginDate, endDate);
        if (unchecked != null && !unchecked.isEmpty()) {
            return EscortStateEnum.等待院外核酸检测结果审核;
        }

        return EscortStateEnum.无核酸检测结果;
    }

    /**
     * 根据陪护证编号查询陪护证实体，同时刷新陪护证状态；独立事务
     * 
     * @param escortNo
     * @return
     */
    @Transactional(propagation = Propagation.PROPAGATION_REQUIRES_NEW)
    public EscortCard QueryEscort(String escortNo) {
        // 查询出陪护证实体
        var escortCard = this.escortMapper.QueryEscort(escortNo);
        if (escortCard == null) {
            return null;
        }

        // 查询状态列表
        escortCard.states = this.escortCardStateMapper.QueryEscortCardStates(escortNo);
        if (escortCard.states == null) {
            return null;
        } else {
            // 若状态发生了改变，则应当更新数据库
            var realState = this.JudgeRealState(escortCard);
            if (realState != ListHelper.GetLast(escortCard.states).state) {
                var newState = new EscortCardState() {
                    {
                        escortNo = null;
                        state = realState;
                        operDate = new Date();
                        remark = "查询到的陪护证状态与实际状态不符，自动更新。";
                    }
                };
                this.escortCardStateMapper.InsertEscortCardState(newState);
                escortCard.states.add(newState);
            }
        }

        // 更新动作列表
        escortCard.actions = this.escortCardActionMapper.QueryEscortCardActions(escortNo);

        return escortCard;
    }

    /**
     * 根据陪护人卡号，查询有效的陪护证
     * 
     * @param helperCardNo 陪护卡号
     * @return 键值对列表<陪护证实体，住院实体>
     */
    @Transactional
    public List<EscortCard> QueryHelperRegisteredEscorts(String helperCardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 初步获取数据库中记录的，与陪护人关联的，尚未注销的陪护证
        var escorts = this.escortMapper.QueryHelperRegisteredEscorts(helperCardNo);

        // 轮训补充部分内部实体
        for (EscortCard escortCard : escorts) {
            // 获取数据库记录的状态列表
            escortCard.states = this.escortCardStateMapper.QueryEscortCardStates(escortCard.escortNo);
            if (escortCard.states == null) {
                continue;
            } else {
                // 若状态发生了改变，则应当更新数据库
                var realState = this.JudgeRealState(escortCard);
                if (realState != ListHelper.GetLast(escortCard.states).state) {
                    var newState = new EscortCardState() {
                        {
                            escortNo = null;
                            state = realState;
                            operDate = new Date();
                            remark = "查询到的陪护证状态与实际状态不符，自动更新。";
                        }
                    };
                    this.escortCardStateMapper.InsertEscortCardState(newState);
                    escortCard.states.add(newState);
                }

                // 如果实际状态已注销，则丢弃这个陪护证
                if (realState == EscortStateEnum.注销) {
                    continue;
                }
            }

            // 获取住院证实体
            if (escortCard.preinCard == null) {
                escortCard.preinCard = this.preinCardMapper.QueryPreinCard(escortCard.patientCardNo,
                        escortCard.happenNo);
                // 获取患者信息实体，若已入院，则存住院实体
                escortCard.preinCard.patient = this.inpatientMapper.QueryInpatientR1(escortCard.patientCardNo,
                        escortCard.happenNo);
                // 若患者未入院，则存患者实体
                if (escortCard.preinCard.patient == null) {
                    escortCard.preinCard.patient = this.patientMapper.QueryPatient(escortCard.patientCardNo);
                }
            }

            // 加入响应结果集
            resultSet.add(escortCard);
        }

        return resultSet;
    }

    /**
     * 根据患者卡号查询关联陪护证
     * 
     * @param patientCardNo 患者就诊卡号
     * @return 陪护证实体
     */
    public List<EscortCard> QueryPatientRegisteredEscorts(String patientCardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 获取最近的一张住院证
        var latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);
        if (latestPreinCard == null) {
            return null;
        }

        // 查询所有关联的未注销陪护证
        var escorts = this.escortMapper.QueryPatientRegisteredEscorts(patientCardNo, latestPreinCard.happenNo);

        // 筛选出有效的陪护证
        for (EscortCard escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 记录住院证
            escort.preinCard = latestPreinCard;

            // 记录helper信息
            escort.helper = this.patientMapper.QueryPatient(escort.helperCardNo);

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo
     * @param newState
     */
    @Transactional
    public void UpdateEscortState(String escortNo, EscortStateEnum newState) {
        // 查询出陪护证实体
        var escort = this.escortMapper.QueryEscort(escortNo);
        if (escort == null) {
            throw new InvalidParameterException("不存在的陪护证");
        }

        // 异常状态
        if (escort.states == null || escort.states.size() == 0) {
            throw new InvalidParameterException("陪护证的初始状态异常");
        }

        // 取当前状态
        var curState = escort.states.get(escort.states.size() - 1);

        // 仅当新状态与当前状态不同时更新
        if (curState.state.equals(newState)) {
            return;
        }

        // 插入一条新状态
        var newEscortState = new EscortCard.EscortState();
        newEscortState.escortNo = curState.escortNo;
        newEscortState.recNo = curState.recNo + 1;
        newEscortState.state = newState;
        newEscortState.operDate = new Date();
        this.escortMapper.InsertEscortState(newEscortState);
    }

    /**
     * 添加陪护前的权限检查
     * 
     * @param escortCard
     */
    private void InsertEscortValidCheck(PreinCard preinCard, String helperCardNo) {
        // 检查住院证状态
        if (preinCard == null || preinCard.state == PreinCardStateEnum.作废) {
            throw new RuntimeException("无有效住院证，无法注册陪护");
        }

        // 检查在院状态
        var inpatient = this.inpatientMapper.QueryInpatientR1(preinCard.cardNo, preinCard.happenNo);
        if (inpatient != null) {
            switch (inpatient.state) {
            case 出院登记:
                throw new RuntimeException("患者已办理出院登记，无法再添加陪护");

            case 出院结算:
                throw new RuntimeException("患者已办理出院结算，无法再添加陪护");

            case 无费退院:
                throw new RuntimeException("患者已办理无费退院，无法再添加陪护");

            default:
                // 其他状态认为是有效的
                break;
            }
        }

        // 患者不可以为自己陪护
        if (helperCardNo.equals(preinCard.cardNo)) {
            throw new InvalidParameterException("患者不可以为自己陪护");
        }

        // 尝试获取陪护人和患者的关联历史
        var historyEscorts = this.escortMapper.QueryHistoryEscorts(preinCard.cardNo, preinCard.happenNo, helperCardNo);
        if (historyEscorts != null && !historyEscorts.isEmpty()) {
            // 获取最近的一次关联
            var targetEscort = historyEscorts.get(historyEscorts.size() - 1);
            var curState = targetEscort.states.get(targetEscort.states.size() - 1);
            if (curState.state == EscortStateEnum.注销) {
                // 默认的时间间隔
                var millionsecond = 12l * 60l * 60l * 1000l;

                // 读取时间间隔配置
                var offsetCfg = this.variableMapper.QueryVariable("EscortRegOffset");
                if (offsetCfg != null && offsetCfg.valid) {
                    millionsecond = Long.parseLong(offsetCfg.value) * 1000;
                }

                // 若最近的一张已注销，判断间隔时间
                var offset = new Date().getTime() - curState.operDate.getTime();
                if (offset <= millionsecond) {
                    throw new RuntimeException(
                            String.format("距离下次登记剩余时间: %d 分钟", (millionsecond - offset) / 1000 / 60));
                }
            } else {
                // 若此时陪护证正生效，则不应该重复添加
                throw new RuntimeException("无法重复添加同一个陪护");
            }
        }

        // 调用查询接口获取患者关联的陪护证
        var patientEscorts = this.QueryActivePatientEscorts(preinCard.cardNo);
        if (patientEscorts != null && !patientEscorts.isEmpty()) {
            // 已有陪护人判断
            switch (patientEscorts.size()) {
            case 0:
                break;

            case 1:
                // 查看患者最近的住院记录
                if (inpatient == null) {
                    throw new RuntimeException("患者尚未入院，无法添加第二陪护");
                } else {
                    // 获取住院医嘱
                    var ordi = this.inpatientOrderMapper.QueryInpatientOrders(inpatient.patientNo, "5070672", 7);
                    if (ordi.isEmpty()) {
                        throw new RuntimeException("患者尚未开立第二陪护医嘱");
                    }
                }
                break;

            default:
                throw new RuntimeException("该患者已关联了两个陪护人，无法添加更多陪护");
            }
        }

        // 调用查询接口获取陪护人关联的陪护证
        var helperEscorts = this.QueryActiveHelperEscorts(helperCardNo);
        if (helperEscorts != null && !helperEscorts.isEmpty()) {
            // 上一个接口以判断唯一键，这里只判断上限
            if (helperEscorts.size() >= 2) {
                throw new RuntimeException("该陪护人已关联了两个患者，无法再为更多人陪护");
            }
        }
    }

    /**
     * 添加一个新的陪护证
     * 
     * @param escortCard
     */
    @Transactional
    public EscortCard InsertEscort(String patientCardNo, String helperCardNo) {
        // 入参判断
        if (patientCardNo == null) {
            throw new InvalidParameterException("患者卡号为空");
        }

        // 查询住院证
        var preinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);

        // 参数、权限检查
        this.InsertEscortValidCheck(preinCard, helperCardNo);

        // 检查VIP记录
        if (preinCard.escortVip == null) {
            preinCard.escortVip = helperCardNo;
            this.preinCardMapper.InsertEscortVip(preinCard);
        }

        // 创建新的陪护证实体
        var newEscortCard = new EscortCard();
        {
            newEscortCard.escortNo = this.escortMapper.GenerateNewEscortNo();
            newEscortCard.patientCardNo = preinCard.cardNo;
            newEscortCard.happenNo = preinCard.happenNo;
            newEscortCard.helperCardNo = helperCardNo;
            newEscortCard.states = new ArrayList<>();
            // 添加初始状态
            var newEscortState = new EscortState();
            {
                newEscortState.escortNo = newEscortCard.escortNo;
                newEscortState.recNo = 1;
                newEscortState.state = EscortStateEnum.无核酸检测结果;
                newEscortState.operDate = new Date();
            }
            newEscortCard.states.add(newEscortState);
        }

        // 插入主表记录
        this.escortMapper.InsertEscort(newEscortCard);

        // 插入状态列表
        this.escortMapper.InsertEscortState(newEscortCard.states.get(0));

        return newEscortCard;
    }

    /**
     * 为陪护人添加附件
     * 
     * @param helperCardNo
     * @param url
     */
    @Transactional
    public void AttachAnnex(String helperCardNo, String picUrl) {
        // 先尝试获取已有附件
        this.escortAnnexMapper.InsertAnnex(helperCardNo, picUrl);
    }
}

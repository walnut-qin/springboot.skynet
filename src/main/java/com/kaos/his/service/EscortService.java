package com.kaos.his.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.kaos.his.entity.credential.EscortAnnexInfo;
import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.credential.EscortCardState;
import com.kaos.his.entity.credential.EscortVip;
import com.kaos.his.entity.credential.PreinCard;
import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.PreinCardStateEnum;
import com.kaos.his.mapper.config.VariableMapper;
import com.kaos.his.mapper.credential.EscortAnnexCheckMapper;
import com.kaos.his.mapper.credential.EscortAnnexInfoMapper;
import com.kaos.his.mapper.credential.EscortCardActionMapper;
import com.kaos.his.mapper.credential.EscortCardMapper;
import com.kaos.his.mapper.credential.EscortCardStateMapper;
import com.kaos.his.mapper.credential.EscortVipMapper;
import com.kaos.his.mapper.credential.PreinCardMapper;
import com.kaos.his.mapper.fee.FinOpbFeeDetailMapper;
import com.kaos.his.mapper.lis.NucleicAcidTestMapper;
import com.kaos.his.mapper.order.InpatientOrderMapper;
import com.kaos.his.mapper.order.OutpatientOrderMapper;
import com.kaos.his.mapper.organization.DepartmentMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;
import com.kaos.his.mapper.personnel.PatientMapper;
import com.kaos.util.ListHelper;

import org.javatuples.Triplet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 陪护证系统的接口
 */
@Service
public class EscortService {
    /**
     * 注解自己
     */
    @Autowired
    EscortService escortService;

    /**
     * 陪护人实体接口
     */
    @Autowired
    EscortCardMapper escortCardMapper;

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
    EscortAnnexInfoMapper escortAnnexInfoMapper;

    /**
     * 附件审核接口
     */
    @Autowired
    EscortAnnexCheckMapper escortAnnexCheckMapper;

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
     * VIP接口
     */
    @Autowired
    EscortVipMapper escortVipMapper;

    /**
     * 门诊划价接口
     */
    @Autowired
    FinOpbFeeDetailMapper finOpbFeeDetailMapper;

    /**
     * 定时任务核心函数：判断陪护证的实际状态
     * 
     * @param escortNo 陪护证编号
     * @return
     */
    private EscortStateEnum JudgeRealState(String escortNo) {
        // 获取陪护证实体
        var escortCard = this.escortCardMapper.QueryEscortCard(escortNo);
        if (escortCard == null) {
            throw new RuntimeException("试图获取不存在的陪护证实体");
        }

        // 优先级0：若记录的状态已经注销，则该陪护证已废弃
        var states = this.escortCardStateMapper.QueryEscortCardStates(escortNo);
        if (ListHelper.GetLast(states).state == EscortStateEnum.注销) {
            return EscortStateEnum.注销;
        }

        // 优先级1：若该陪护证关联的住院证已过期，则陪护证失效
        var latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(escortCard.patientCardNo);
        if (latestPreinCard == null || !latestPreinCard.happenNo.equals(escortCard.happenNo)) {
            return EscortStateEnum.注销;
        }

        // 优先级2：若关联的住院实体已出院，则也失效
        var relateInpatient = this.inpatientMapper.QueryInpatientR1(escortCard.patientCardNo, escortCard.happenNo);
        if (relateInpatient != null) {
            switch (relateInpatient.state) {
            case 无费退院:
            case 出院结算:
                return EscortStateEnum.注销;

            default:
                break;
            }
        }

        // 优先级3：若陪护人7日内院内核酸结果为阴性，则生效陪护证
        var nats = this.nucleicAcidTestMapper.QueryNucleicAcidTest(escortCard.helperCardNo, "SARS-CoV-2-RNA", 7);
        if (nats != null && !nats.isEmpty() && ListHelper.GetLast(nats).negative) {
            return EscortStateEnum.生效中;
        }

        // 优先级4：若陪护人已审核的院外报告中，最近的一个结果为阴性，则生效陪护证
        var rpt = this.escortAnnexCheckMapper.QueryLastExecEscortAnnexCheck(escortCard.helperCardNo);
        if (rpt != null && rpt.negative && (new Date().getTime() - rpt.execDate.getTime() <= 7 * 24 * 60 * 60 * 1000)) {
            return EscortStateEnum.生效中;
        }

        // 优先级5-1：判断免费核酸划价记录
        var feeDetail = this.finOpbFeeDetailMapper.QueryLastFinOpbFeeDetail(escortCard.helperCardNo, "F00000068231");
        if (feeDetail != null && (new Date().getTime() - feeDetail.operDate.getTime() <= 7 * 24 * 60 * 60 * 1000)) {
            // 有7日内的免费核酸划价
            return EscortStateEnum.等待院内核酸检测结果;
        }

        // 优先级5：判断院内核酸医嘱
        var ordRs = this.outpatientOrderMapper.QueryOutpatientOrders(escortCard.helperCardNo, "438771", 7);
        if (ordRs != null && !ordRs.isEmpty()) {
            // 有院内医嘱
            return EscortStateEnum.等待院内核酸检测结果;
        }

        // 优先级6：判断有无院外待审核结果
        var unchecked = this.escortAnnexInfoMapper.QueryUncheckedEscortAnnexInfos(escortCard.helperCardNo);
        if (unchecked != null && !unchecked.isEmpty()) {
            return EscortStateEnum.等待院外核酸检测结果审核;
        }

        return EscortStateEnum.无核酸检测结果;
    }

    /**
     * 刷新陪护证状态（定时任务）
     * 
     * @param escortNo
     */
    @Transactional
    public void RefreshEscortState(String targetEscortNo) {
        // 获取数据库状态列表
        var states = this.escortCardStateMapper.QueryEscortCardStates(targetEscortNo);
        if (states == null || states.isEmpty()) {
            throw new RuntimeException("检测到异常状态的陪护证");
        }

        // 获取实时状态
        var realState = this.JudgeRealState(targetEscortNo);

        // 若状态发生了变化，则更新
        if (realState != ListHelper.GetLast(states).state) {
            var newState = new EscortCardState() {
                {
                    escortNo = targetEscortNo;
                    state = realState;
                    operDate = new Date();
                    remark = "探测到陪护证状态变更，自动更新。";
                }
            };
            this.escortCardStateMapper.InsertEscortCardState(newState);
        }
    }

    /**
     * 更新陪护证状态
     * 
     * @param escortNo
     * @param newState
     */
    @Transactional
    public void UpdateEscortState(String targetEscortNo, EscortStateEnum newState) {
        if (targetEscortNo == null || newState == null) {
            throw new RuntimeException("陪护证号和状态不能为空");
        }

        // 构造新的状态实体
        var newEscortState = new EscortCardState() {
            {
                escortNo = targetEscortNo;
                state = newState;
                operDate = new Date();
                remark = "收到更新状态请求";
            }
        };

        // 执行插入操作
        this.escortCardStateMapper.InsertEscortCardState(newEscortState);
    }

    /**
     * 根据陪护证编号查询陪护证实体
     * 
     * @param escortNo
     * @return
     */
    public EscortCard QueryEscort(String escortNo) {
        var escortCard = this.escortCardMapper.QueryEscortCard(escortNo);
        if (escortCard == null) {
            return null;
        }

        // 获取状态
        escortCard.states = this.escortCardStateMapper.QueryEscortCardStates(escortNo);

        // 获取动作
        escortCard.actions = this.escortCardActionMapper.QueryEscortCardActions(escortNo);

        // 查询出陪护证实体
        return escortCard;
    }

    /**
     * 根据陪护人卡号，查询有效的陪护证
     * 
     * @param helperCardNo 陪护卡号
     * @return 键值对列表<陪护证实体，住院实体>
     */
    public List<EscortCard> QueryHelperRegisteredEscorts(String helperCardNo) {
        var escortCards = this.escortCardMapper.QueryHelperRegisteredEscortCards(helperCardNo);
        if (escortCards == null) {
            return null;
        }

        for (EscortCard escortCard : escortCards) {
            // 填充住院证
            escortCard.preinCard = this.preinCardMapper.QueryPreinCard(escortCard.patientCardNo, escortCard.happenNo);

            // 填充住院实体
            var inpatient = this.inpatientMapper.QueryInpatientR1(escortCard.patientCardNo, escortCard.happenNo);
            if (inpatient != null) {
                inpatient.dept = this.departmentMapper.QueryDepartment(inpatient.deptCode);
                escortCard.preinCard.patient = inpatient;
            } else {
                escortCard.preinCard.patient = this.patientMapper.QueryPatient(escortCard.patientCardNo);
                escortCard.preinCard.preDept = this.departmentMapper.QueryDepartment(escortCard.preinCard.preDeptCode);
            }

            // 填充VIP实体
            escortCard.escortVip = this.escortVipMapper.QueryEscortVip(escortCard.patientCardNo, escortCard.happenNo);
        }

        // 初步获取数据库中记录的，与陪护人关联的，尚未注销的陪护证
        return escortCards;

    }

    /**
     * 根据患者卡号查询关联陪护证
     * 
     * @param patientCardNo 患者就诊卡号
     * @return 陪护证实体
     */
    public List<EscortCard> QueryPatientRegisteredEscorts(String patientCardNo) {
        // 获取最近一次住院证
        var preinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);
        if (preinCard == null) {
            throw new RuntimeException("检测到不存在的住院证");
        }

        // 获取关联陪护证
        var escortCards = this.escortCardMapper.QueryPatientRegisteredEscortCards(patientCardNo, preinCard.happenNo);
        if (escortCards == null) {
            return null;
        }

        for (EscortCard escortCard : escortCards) {
            // 填充陪护人实体
            escortCard.helper = this.patientMapper.QueryPatient(escortCard.helperCardNo);

            // 填充VIP实体
            escortCard.escortVip = this.escortVipMapper.QueryEscortVip(escortCard.patientCardNo, escortCard.happenNo);
        }

        // 初步获取数据库中记录的，与陪护人关联的，尚未注销的陪护证
        return escortCards;
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

        // 患者不可以为自己陪护
        if (helperCardNo.equals(preinCard.cardNo)) {
            throw new InvalidParameterException("患者不可以为自己陪护");
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

        // 12小时内已注销
        var preRec = this.escortCardMapper.QueryLastEscortCards(preinCard.cardNo, preinCard.happenNo, helperCardNo);
        if (preRec != null) {
            var states = this.escortCardStateMapper.QueryEscortCardStates(preRec.escortNo);
            if (ListHelper.GetLast(states).state == EscortStateEnum.注销) {
                // 默认的时间间隔
                var millionsecond = 12l * 60l * 60l * 1000l;

                // 读取时间间隔配置
                var offsetCfg = this.variableMapper.QueryVariable("EscortRegOffset");
                if (offsetCfg != null && offsetCfg.valid) {
                    millionsecond = Long.parseLong(offsetCfg.value) * 1000;
                }

                // 若最近的一张已注销，判断间隔时间
                var offset = new Date().getTime() - ListHelper.GetLast(states).operDate.getTime();
                if (offset <= millionsecond) {
                    Integer leftM = (int) Math.ceil((millionsecond - offset) / 1000.00 / 60.00);
                    Integer leftH = leftM / 60;
                    leftM %= 60;
                    if (leftH > 0) {
                        throw new RuntimeException(String.format("距离下次登记剩余时间: %d 小时 %d 分钟", leftH, leftM));
                    } else {
                        throw new RuntimeException(String.format("距离下次登记剩余时间: %d 分钟", leftM));
                    }
                }
            } else {
                // 若此时陪护证正生效，则不应该重复添加
                throw new RuntimeException("无法重复添加同一个陪护");
            }
        }

        // 调用查询接口获取患者关联的陪护证
        var patientEscorts = this.QueryPatientRegisteredEscorts(preinCard.cardNo);
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
        var helperEscorts = this.QueryHelperRegisteredEscorts(helperCardNo);
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
        // 查询住院证
        var preinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);

        // 可行性检查
        this.InsertEscortValidCheck(preinCard, helperCardNo);

        // 检查VIP记录
        var vipRec = this.escortVipMapper.QueryEscortVip(preinCard.cardNo, preinCard.happenNo);
        if (vipRec == null) {
            var newVipRec = new EscortVip();
            newVipRec.patientCardNo = patientCardNo;
            newVipRec.happenNo = preinCard.happenNo;
            newVipRec.helperCardNo = helperCardNo;
            newVipRec.operDate = new Date();
            newVipRec.remark = "";
            this.escortVipMapper.InsertEscortVip(newVipRec);
        }

        // 插入主表记录
        var newEscortCard = new EscortCard();
        newEscortCard.escortNo = null;
        newEscortCard.patientCardNo = patientCardNo;
        newEscortCard.happenNo = preinCard.happenNo;
        newEscortCard.helperCardNo = helperCardNo;
        newEscortCard.operDate = new Date();
        newEscortCard.remark = "";
        this.escortCardMapper.InsertEscortCard(newEscortCard);

        // 插入状态表记录
        var newEscortState = new EscortCardState();
        {
            newEscortState.escortNo = newEscortCard.escortNo;
            newEscortState.state = EscortStateEnum.无核酸检测结果;
            newEscortState.operDate = new Date();
            newEscortState.remark = "添加陪护证，初始状态";
        }
        this.escortCardStateMapper.InsertEscortCardState(newEscortState);

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
        this.escortAnnexInfoMapper.InsertEscortAnnexInfo(new EscortAnnexInfo() {
            {
                annexNo = null;
                cardNo = helperCardNo;
                annexUrl = picUrl;
                operDate = new Date();
            }
        });
    }

    /**
     * 查询某个科室下的所有未审核的记录
     * 
     * @param deptCode 科室编码
     * @return Map<陪护人卡号， Triplet<陪护人实体， 陪护附件实体， 被陪护人列表>>
     */
    public HashMap<String, Triplet<Patient, EscortAnnexInfo, List<Patient>>> QueryUncheckedAnnexInfo(String deptCode) {
        // 创建结果集
        var rs = new HashMap<String, Triplet<Patient, EscortAnnexInfo, List<Patient>>>();

        // 查询出所有未审核的附件
        var annexs = this.escortAnnexInfoMapper.QueryAllUncheckedEscortAnnexInfos();

        // 基本信息寄存器
        var patientInfos = new HashMap<String, Patient>();

        // 轮训所有未审核附件，找出符合条件的
        for (EscortAnnexInfo escortAnnexInfo : annexs) {
            // 查询出陪护人基本信息
            var helperInfo = patientInfos.get(escortAnnexInfo.cardNo);
            if (helperInfo == null) {
                helperInfo = this.patientMapper.QueryPatient(escortAnnexInfo.cardNo);
                patientInfos.put(escortAnnexInfo.cardNo, helperInfo);
            }

            // 查询该陪护人关联的陪护证
            var escortCards = this.escortCardMapper.QueryHelperRegisteredEscortCards(escortAnnexInfo.cardNo);

            // 查询出所有关联的患者
            for (EscortCard escortCard : escortCards) {
                // 查询出关联的住院证
                var preinCard = this.preinCardMapper.QueryPreinCard(escortCard.patientCardNo, escortCard.happenNo);

                // 若住院证科室符合条件 - 加入结果集
                if (preinCard.preDeptCode.equals(deptCode)) {
                    // 查询出患者基本信息
                    var patientInfo = patientInfos.get(escortCard.patientCardNo);
                    if (patientInfo == null) {
                        patientInfo = this.patientMapper.QueryPatient(escortCard.patientCardNo);
                        patientInfos.put(escortCard.patientCardNo, patientInfo);
                    }

                    if (rs.keySet().contains(helperInfo.cardNo)) {
                        rs.get(helperInfo.cardNo).getValue2().add(patientInfo);
                    } else {
                        var item = new Triplet<Patient, EscortAnnexInfo, List<Patient>>(helperInfo, escortAnnexInfo,
                                new ArrayList<Patient>());
                        item.getValue2().add(patientInfo);
                        rs.put(helperInfo.cardNo, item);
                    }
                }
            }
        }

        return rs;
    }

    /**
     * 查询当前数据库所有活跃的陪护证
     * 
     * @return
     */
    public List<EscortCard> QueryAllRegisteredEscortNo() {
        return this.escortCardMapper.QueryAllActivedEscortCards();
    }
}

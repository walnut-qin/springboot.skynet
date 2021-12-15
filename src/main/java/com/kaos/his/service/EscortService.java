package com.kaos.his.service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaos.his.entity.credential.EscortAnnex;
import com.kaos.his.entity.credential.EscortCard;
import com.kaos.his.entity.credential.PreinCard;
import com.kaos.his.entity.credential.EscortCard.EscortState;
import com.kaos.his.enums.EscortStateEnum;
import com.kaos.his.enums.PreinCardStateEnum;
import com.kaos.his.mapper.config.VariableMapper;
import com.kaos.his.mapper.credential.EscortAnnexMapper;
import com.kaos.his.mapper.credential.EscortCardMapper;
import com.kaos.his.mapper.credential.PreinCardMapper;
import com.kaos.his.mapper.lis.NucleicAcidTestMapper;
import com.kaos.his.mapper.order.InpatientOrderMapper;
import com.kaos.his.mapper.order.OutpatientOrderMapper;
import com.kaos.his.mapper.organization.DepartmentMapper;
import com.kaos.his.mapper.personnel.InpatientMapper;
import com.kaos.his.mapper.personnel.PatientMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
     * 陪护附件接口
     */
    @Autowired
    EscortAnnexMapper escortAnnexMapper;

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
     * 根据陪护人卡号，查询有效的陪护证
     * 
     * @param helperCardNo 陪护卡号
     * @return 键值对列表<陪护证实体，住院实体>
     */
    public List<EscortCard> QueryActiveHelperEscorts(String helperCardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.QueryHelperEscorts(helperCardNo);

        // 辅助字典 - 记录患者的最近一次住院证，加速大批量数据时的查询
        Map<String, PreinCard> preinCardDict = new HashMap<String, PreinCard>();

        // 筛选出有效的陪护证
        for (EscortCard escort : escorts) {
            // 如果状态为注销，则陪护证是无效的
            if (escort.states.isEmpty() || escort.states.get(escort.states.size() - 1).state == EscortStateEnum.注销) {
                continue;
            }

            // 获取陪护证关联的住院证
            escort.preinCard = this.preinCardMapper.QueryPreinCard(escort.patientCardNo, escort.happenNo);

            // 记录预约的科室
            escort.preinCard.preDept = this.departmentMapper.QueryDepartment(escort.preinCard.preDeptCode);

            // 获取患者最近的住院证
            PreinCard latestPreinCard = null;
            if (preinCardDict.containsKey(escort.preinCard.cardNo)) {
                latestPreinCard = preinCardDict.get(escort.patientCardNo);
            } else {
                latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(escort.patientCardNo);
                preinCardDict.put(escort.preinCard.cardNo, latestPreinCard);
            }

            // 如果住院证不是患者最近的一张陪护证，说明被关联的患者已经出院，陪护证应当自动失效
            if (escort.preinCard.happenNo != latestPreinCard.happenNo) {
                continue;
            }

            // 如果已入院，则将住院患者实体更新为住院实体，否则记录患者信息
            var inpatient = this.inpatientMapper.QueryInpatientR1(escort.patientCardNo, escort.happenNo);
            if (inpatient != null) {
                // 记录科室信息
                inpatient.dept = this.departmentMapper.QueryDepartment(inpatient.deptCode);
                // 更新记录
                escort.preinCard.patient = inpatient;
            } else {
                escort.preinCard.patient = this.patientMapper.QueryPatient(escort.patientCardNo);
            }

            // 加入结果集
            resultSet.add(escort);
        }

        return resultSet;
    }

    /**
     * 根据患者卡号查询关联陪护证
     * 
     * @param patientCardNo 患者就诊卡号
     * @return 陪护证实体
     */
    public List<EscortCard> QueryActivePatientEscorts(String patientCardNo) {
        // 声明结果集
        var resultSet = new ArrayList<EscortCard>();

        // 获取最近的一张住院证
        var latestPreinCard = this.preinCardMapper.QueryLatestPreinCard(patientCardNo);
        if (latestPreinCard == null) {
            throw new RuntimeException("患者无住院证");
        }

        // 查询所有关联的陪护证
        var escorts = this.escortMapper.QueryPatientEscorts(patientCardNo, latestPreinCard.happenNo);

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
     * 根据陪护证编号查询陪护证实体
     * 
     * @param escortNo
     * @return
     */
    @Transactional
    public EscortCard QueryEscort(String escortNo) {
        // 查询出陪护证实体
        var escort = this.escortMapper.QueryEscort(escortNo);
        if (escort == null || escort.states == null || escort.states.isEmpty()) {
            return escort;
        }

        // 获取记录的当前状态，特殊状态不判断
        var curState = escort.states.get(escort.states.size() - 1);
        switch (curState.state) {
        case 等待院外核酸检测结果审核:
        case 注销:
            return escort;

        default:
            break;
        }

        // 声明实际状态
        EscortStateEnum realState = null;

        // 查询是否存在核酸检测结果
        var acidRs = this.nucleicAcidTestMapper.QueryNucleicAcidTest(escort.helperCardNo, "SARS-CoV-2-RNA", 7);
        if (acidRs == null || acidRs.isEmpty()) {
            // 若不存在核酸结果，查询是否存在核酸医嘱
            var ordRs = this.outpatientOrderMapper.QueryOutpatientOrders(escort.helperCardNo, "438771", 7);
            if (ordRs == null || ordRs.isEmpty()) {
                // 若无医嘱，则为尚未开立核酸医嘱状态
                realState = EscortStateEnum.无核酸检测结果;
            } else {
                // 若有医嘱，则为等待结果状态
                realState = EscortStateEnum.等待院内核酸检测结果;
            }
        } else {
            // 取最近的一次结果
            var curRt = acidRs.get(acidRs.size() - 1);
            if (curRt.negative) {
                // 核酸为阴性
                realState = EscortStateEnum.生效中;
            } else {
                // 核酸为阳性
                realState = EscortStateEnum.无核酸检测结果;
            }
        }

        // 如果新状态与记录的状态不等，则更新
        if (realState != curState.state) {
            // 创建新状态
            var newItem = new EscortCard.EscortState();
            newItem.escortNo = curState.escortNo;
            newItem.recNo = curState.recNo + 1;
            newItem.state = realState;
            newItem.operDate = new Date();

            // 将新状态插入列表
            this.escortMapper.InsertEscortState(newItem);
            escort.states.add(newItem);
        }

        return escort;
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
        var annex = this.escortAnnexMapper.QueryEscortAnnex(helperCardNo);

        if (annex == null) {
            // 如果原来不存在，插入新记录
            this.escortAnnexMapper.InsertEscortAnnex(new EscortAnnex() {
                {
                    escortCardNo = helperCardNo;
                    operDate = new Date();
                    url = picUrl;
                }
            });
        } else {
            // 如果存在，则修改
            this.escortAnnexMapper.UpdateEscortAnnex(helperCardNo, new Date(), picUrl);
        }
    }
}

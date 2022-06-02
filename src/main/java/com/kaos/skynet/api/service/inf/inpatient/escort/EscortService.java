package com.kaos.skynet.api.service.inf.inpatient.escort;

import java.util.List;

import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortMainInfo;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortActionRec.ActionEnum;
import com.kaos.skynet.api.data.entity.inpatient.escort.EscortStateRec.StateEnum;

public interface EscortService {
    /**
     * 注册陪护人
     * 
     * @param patientCardNo
     * @param helperCardNo
     * @param emplCode
     * @param remark
     * @return
     */
    EscortMainInfo registerEscort(String patientCardNo, String helperCardNo, String emplCode, String remark,
            Boolean regByWindow);

    /**
     * 修改陪护证状态
     * 
     * @param escortNo
     * @param state
     * @param emplCode
     * @param remark
     * @return
     */
    EscortStateRec updateEscortState(String escortNo, StateEnum state, String emplCode, String remark);

    /**
     * 记录陪护动作
     * 
     * @param escortNo
     * @param action
     * @param remark
     * @return
     */
    EscortActionRec recordEscortAction(String escortNo, ActionEnum action, String remark);

    /**
     * 查询陪护证，主键查询
     * 
     * @param escortNo
     * @return
     */
    EscortMainInfo queryEscortStateInfo(String escortNo);

    /**
     * 查询被陪护的患者
     * 
     * @param patientCardNo
     * @return
     */
    List<EscortMainInfo> queryPatientInfos(String helperCardNo);

    /**
     * 查询陪护人信息
     * 
     * @param patientCardNo
     * @return
     */
    List<EscortMainInfo> queryHelperInfos(String patientCardNo);

    /**
     * 查询所有当前未注销的陪护证号
     * 
     * @return
     */
    List<String> queryUncanceledEscortNos();
}

package com.kaos.his.service.inpatient.escort;

import java.util.List;

import com.kaos.his.entity.inpatient.escort.EscortActionRec;
import com.kaos.his.entity.inpatient.escort.EscortMainInfo;
import com.kaos.his.entity.inpatient.escort.EscortStateRec;
import com.kaos.his.enums.inpatient.escort.EscortActionEnum;
import com.kaos.his.enums.inpatient.escort.EscortStateEnum;

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
    EscortMainInfo registerEscort(String patientCardNo, String helperCardNo, String emplCode, String remark);

    /**
     * 修改陪护证状态
     * 
     * @param escortNo
     * @param state
     * @param emplCode
     * @param remark
     * @return
     */
    EscortStateRec updateEscortState(String escortNo, EscortStateEnum state, String emplCode, String remark);

    /**
     * 记录陪护动作
     * 
     * @param escortNo
     * @param action
     * @param remark
     * @return
     */
    EscortActionRec recordEscortAction(String escortNo, EscortActionEnum action, String remark);

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

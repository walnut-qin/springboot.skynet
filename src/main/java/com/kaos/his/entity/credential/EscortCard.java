package com.kaos.his.entity.credential;

import java.util.Date;
import java.util.List;

import com.kaos.his.entity.personnel.Patient;
import com.kaos.his.enums.EscortActionEnum;
import com.kaos.his.enums.EscortStateEnum;

public class EscortCard {
    /**
     * 陪护证状态
     */
    public static class EscortState {
        /**
         * 陪护证编号
         */
        public String escortNo = null;

        /**
         * 状态记录号
         */
        public Integer recNo = null;

        /**
         * 状态值
         */
        public EscortStateEnum state = null;

        /**
         * 状态记录生成时间
         */
        public Date operDate = null;
    }

    /**
     * 陪护证行为
     */
    public static class EscortAction {
        /**
         * 陪护证编号
         */
        public String escortNo = null;

        /**
         * 状态记录号
         */
        public Integer recNo = null;

        /**
         * 状态值
         */
        public EscortActionEnum action = null;

        /**
         * 状态记录生成时间
         */
        public Date operDate = null;
    }

    /**
     * 陪护证编号
     */
    public String escortNo;

    /**
     * 患者就诊卡号
     */
    public String patientCardNo = null;

    /**
     * 住院证序号
     */
    public Integer happenNo = null;

    /**
     * 陪护人卡号
     */
    public String helperCardNo = null;

    /**
     * 关联的住院证
     */
    public PreinCard preinCard;

    /**
     * 关联的陪护人
     */
    public Patient helper;

    /**
     * 状态清单
     */
    public List<EscortState> states;

    /**
     * 行为清单
     */
    public List<EscortAction> actions;
}

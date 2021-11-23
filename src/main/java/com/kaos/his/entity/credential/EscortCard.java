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
         * 状态记录号
         */
        public Integer recNo;

        /**
         * 状态值
         */
        public EscortStateEnum state;

        /**
         * 状态记录生成时间
         */
        public Date operDate;
    }

    /**
     * 陪护证行为
     */
    public static class EscortAction {
        /**
         * 状态记录号
         */
        public Integer recNo;

        /**
         * 状态值
         */
        public EscortActionEnum action;

        /**
         * 状态记录生成时间
         */
        public Date operDate;
    }

    /**
     * 陪护证编号
     */
    public String escortNo;

    /**
     * 关联的住院证
     */
    public PreinCard preinCard;

    /**
     * 关联的陪护人
     */
    public Patient helper;

    /**
     * VIP标识
     */
    public boolean vip;

    /**
     * 状态清单
     */
    public List<EscortState> states;

    /**
     * 行为清单
     */
    public List<EscortAction> actions;
}

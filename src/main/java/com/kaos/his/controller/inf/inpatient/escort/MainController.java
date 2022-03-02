package com.kaos.his.controller.inf.inpatient.escort;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.google.gson.annotations.SerializedName;
import com.kaos.his.enums.impl.common.SexEnum;
import com.kaos.his.enums.impl.inpatient.escort.EscortActionEnum;
import com.kaos.his.enums.impl.inpatient.escort.EscortStateEnum;

public interface MainController {
    /**
     * 登记陪护人
     * 
     * @param patientCardNo 患者卡号
     * @param helperCardNo  陪护人卡号
     * @param emplCode      操作员编码
     * @return
     */
    String register(@NotNull(message = "患者卡号不能为空") String patientCardNo,
            @NotNull(message = "陪护人卡号不能为空") String helperCardNo,
            @NotNull(message = "操作员编码不能为空") String emplCode,
            String remark);

    /**
     * 更新陪护证状态
     * 
     * @param escortNo 陪护证号
     * @param state    新状态
     * @param emplCode 操作员
     */
    void updateState(@NotNull(message = "陪护证号不能为空") String escortNo,
            @NotNull(message = "新状态不能为空") EscortStateEnum state,
            @NotNull(message = "操作员编码不能为空") String emplCode);

    /**
     * 记录陪护状态
     * 
     * @param escortNo 陪护证号
     * @param action   记录的动作
     */
    void recordAction(@NotNull(message = "陪护证号不能为空") String escortNo,
            @NotNull(message = "记录的动作不能为空") EscortActionEnum action);

    /**
     * 查询陪护证状态
     * 
     * @param escortNo 陪护证号
     * @return
     */
    QueryStateInfoRsp queryStateInfo(@NotNull(message = "陪护证号不能为空") String escortNo);

    /**
     * 查询状态的响应
     */
    public static class QueryStateInfoRsp {
        /**
         * 患者卡号
         */
        public String patientCardNo = null;

        /**
         * 陪护人卡号
         */
        @SerializedName(value = "escortCardNo")
        public String helperCardNo = null;

        /**
         * 注册时间
         */
        public Date regDate = null;

        /**
         * 当前状态<枚举值>
         */
        public String state = null;
    }

    /**
     * 查询关联的患者信息
     * 
     * @param helperCardNo 陪护人卡号
     * @return
     */
    List<QueryPatientsRsp> queryPatients(@NotNull(message = "陪护人卡号不能为空") String helperCardNo);

    /**
     * 查询患者信息响应
     */
    public static class QueryPatientsRsp {
        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 科室
         */
        public String deptName = null;

        /**
         * 床号
         */
        public String bedNo = null;

        /**
         * 住院号
         */
        public String patientNo = null;

        /**
         * 免费标识
         */
        public String freeFlag = null;

        /**
         * 陪护证号
         */
        public String escortNo = null;

        /**
         * 状态列表
         */
        public List<EscortStateRec> states = null;

        /**
         * 动作列表
         */
        public List<EscortActionRec> actions = null;
    }

    /**
     * 查询陪护人信息
     * 
     * @param patientCardNo 患者就诊卡号
     * @return
     */
    List<QueryHelpersRsp> queryHelpers(@NotNull(message = "患者卡号不能为空") String patientCardNo);

    /**
     * 查询陪护人响应
     */
    public static class QueryHelpersRsp {
        /**
         * 就诊卡号
         */
        public String cardNo = null;

        /**
         * 姓名
         */
        public String name = null;

        /**
         * 性别
         */
        public SexEnum sex = null;

        /**
         * 年龄
         */
        public String age = null;

        /**
         * 免费标识： 0 - 不免费 1 - 免费
         */
        public String freeFlag = null;

        /**
         * 陪护证号
         */
        public String escortNo = null;

        /**
         * 状态列表
         */
        public List<EscortStateRec> states = null;

        /**
         * 行为列表
         */
        public List<EscortActionRec> actions = null;
    }

    /**
     * 响应状态记录
     */
    public static class EscortStateRec {
        /**
         * 状态序号
         */
        public Integer recNo = null;

        /**
         * 状态
         */
        public EscortStateEnum state = null;

        /**
         * 记录员编码
         */
        public String recEmplCode = null;

        /**
         * 记录时间
         */
        public Date recDate = null;
    }

    /**
     * 响应动作记录
     */
    public static class EscortActionRec {
        /**
         * 状态序号
         */
        public Integer recNo = null;

        /**
         * 状态
         */
        public EscortActionEnum action = null;

        /**
         * 记录时间
         */
        public Date recDate = null;
    }
}

package com.kaos.his.controller.surgery.entity;

import java.util.Date;
import java.util.List;

import com.kaos.his.enums.AnesTypeEnum;
import com.kaos.his.enums.SexEnum;
import com.kaos.his.enums.SurgeryDegreeEnum;
import com.kaos.his.enums.SurgeryInspectResultEnum;

/**
 * 接口 QueryMetOpsAppliesInDept 的响应体
 */
public class QueryArrangedMetOpsAppliesInDeptRspBody {
    /**
     * 手术间
     */
    public String roomNo = null;

    /**
     * 预约时间
     */
    public Date apprDate = null;

    /**
     * 住院号
     */
    public String patientNo = null;

    /**
     * 科室
     */
    public String deptName = null;

    /**
     * 床号
     */
    public String bedNo = null;

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
     * 诊断
     */
    public String diagnosis = null;

    /**
     * 手术名称
     */
    public String surgeryName = null;

    /**
     * 手术标识
     */
    public String operRemark = null;

    /**
     * 手术分级
     */
    public SurgeryDegreeEnum degree = null;

    /**
     * 主刀医师姓名
     */
    public String surgeryDocName = null;

    /**
     * 助手姓名
     */
    public List<String> helperNames = null;

    /**
     * 麻醉种类
     */
    public AnesTypeEnum anesType = null;

    /**
     * 麻醉医生姓名
     */
    public List<String> anesDocNames = null;

    /**
     * 洗手护士
     */
    public List<String> washNurseNames = null;

    /**
     * 巡回护士
     */
    public List<String> itinerantNurseNames = null;

    /**
     * 申请备注
     */
    public String applyNote = null;

    /**
     * ERAS信息
     */
    public String eras = null;

    /**
     * VTE信息
     */
    public String vte = null;

    /**
     * 检验结果
     */
    public SurgeryInspectResultEnum inspectResult = null;

    /**
     * 是否公布
     */
    public String publishFlag = null;
}

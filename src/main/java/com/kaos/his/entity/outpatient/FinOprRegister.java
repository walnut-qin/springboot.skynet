package com.kaos.his.entity.outpatient;

import java.util.Date;

import com.kaos.his.entity.common.DawnOrgDept;
import com.kaos.his.entity.common.Patient;
import com.kaos.his.enums.common.NoonEnum;
import com.kaos.his.enums.common.SexEnum;
import com.kaos.his.enums.common.TransTypeEnum;

/**
 * 门诊挂号实体
 */
public class FinOprRegister {
    /**
     * 门诊号
     */
    public String clinicCode = null;

    /**
     * 交易类型
     */
    public TransTypeEnum transType = null;

    /**
     * 就诊卡号
     */
    public String cardNo = null;

    /**
     * 挂号日期
     */
    public Date regDate = null;

    /**
     * 午别
     */
    public NoonEnum noon = null;

    /**
     * 姓名
     */
    public String name = null;

    /**
     * 身份证号
     */
    public String idenNo = null;

    /**
     * 性别
     */
    public SexEnum sex = null;

    /**
     * 生日
     */
    public Date birthday = null;

    /**
     * 联系电话
     */
    public String relaPhone = null;

    /**
     * 家庭住址
     */
    public String address = null;

    /**
     * 证件类型
     */
    public String cardType = null;

    /**
     * 结算类别号
     */
    public String payKindCode = null;

    /**
     * 结算类别名
     */
    public String payKindName = null;

    /**
     * 合同号
     */
    public String pactCode = null;

    /**
     * 合同单位名
     */
    public String pactName = null;

    /**
     * 医疗证号
     */
    public String medCardNo = null;

    /**
     * 挂号级别编码
     */
    public String regLevlCode = null;

    /**
     * 挂号级别名称
     */
    public String regLevlName = null;

    /**
     * 科室编码
     */
    public String deptCode = null;

    /**
     * 科室名称
     */
    public String deptName = null;

    /**
     * 排班序号
     */
    public String schemaNo = null;

    /**
     * 每日顺序号
     */
    public String orderNo = null;

    /**
     * 看诊序号
     */
    public String seeNo = null;

    /**
     * 看诊开始时间
     */
    public Date beginTime = null;

    /**
     * 看诊结束时间
     */
    public Date endTime = null;

    /**
     * 医师编码
     */
    public String doctCode = null;

    /**
     * 医师姓名
     */
    public String doctName = null;

    /**
     * 关联实体
     */
    public class AssociateEntity {
        /**
         * 患者实体
         */
        public Patient patient = null;

        /**
         * 挂号科室
         */
        public DawnOrgDept regDept = null;
    }

    /**
     * 关联实体
     */
    transient public AssociateEntity associateEntity = new AssociateEntity();
}

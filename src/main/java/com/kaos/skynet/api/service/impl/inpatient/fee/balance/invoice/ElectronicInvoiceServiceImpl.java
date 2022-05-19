package com.kaos.skynet.api.service.impl.inpatient.fee.balance.invoice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.kaos.skynet.api.entity.inpatient.fee.balance.invoice.electronic.FinComElectronicInvoice;
import com.kaos.skynet.api.service.inf.inpatient.fee.balance.invoice.ElectronicInvoiceService;
import com.kaos.skynet.enums.common.HospitalTypeEnum;
import com.kaos.skynet.enums.common.SexEnum;
import com.kaos.skynet.enums.common.SourceTypeEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.BusinessTypeEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.CardTypeEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.PayerTypeEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.PlaceCodeEnum;

import org.springframework.stereotype.Service;

import lombok.Data;

@Service
public class ElectronicInvoiceServiceImpl implements ElectronicInvoiceService {
    @Override
    public FinComElectronicInvoice register(String invoiceNo, SourceTypeEnum sourceType, String alipayCode,
            String weChatOrderNo, String openId) {
        return null;
    }

    @Data
    private static class RegisterReqBody {
        /**
         * 业务流水号
         */
        private String busNo = null;

        /**
         * 业务类型
         */
        private BusinessTypeEnum busType = null;

        /**
         * 患者姓名
         */
        private String payer = null;

        /**
         * 业务发生时间
         * {@link LocalDateTime#format(DateTimeFormatter)}
         * {@link DateTimeFormatter#ofPattern(String)}
         * {@value "yyyyMMddHHmmssfff"}
         */
        private LocalDateTime busDateTime = null;

        /**
         * 开票点编码
         */
        private PlaceCodeEnum placeCode = null;

        /**
         * 收费员
         */
        private String payee = null;

        /**
         * 编制人
         */
        private String author = null;

        /**
         * 票据审核人
         */
        private String checker = null;

        /**
         * 开票总金额
         */
        private String totalAmt = null;

        /**
         * 备注
         */
        private String remark = null;

        /**
         * 患者支付宝账户
         */
        private String alipayCode = null;

        /**
         * 微信支付订单号
         */
        private String weChatOrderNo = null;

        /**
         * 微信医保支付
         */
        private String weChatMedTransNo = null;

        /**
         * 微信公众号或小程序用户ID
         */
        private String openId = null;

        /**
         * 患者联系电话
         */
        private String tel = null;

        /**
         * 患者邮箱
         */
        private String email = null;

        /**
         * 缴款人类型
         */
        private PayerTypeEnum payerType = null;

        /**
         * 证件类型
         */
        private String idCardType = null;

        /**
         * 身份证号
         */
        private String idCardNo = null;

        /**
         * 卡类型
         */
        private CardTypeEnum cardType = null;

        /**
         * 卡号
         */
        private String cardNo = null;

        /**
         * 医院类型
         */
        private HospitalTypeEnum medicalInstitution = null;

        /**
         * 医保机构编码
         */
        private String medCareInstitution = null;

        /**
         * 医保类型编码
         */
        private String medCareTypeCode = null;

        /**
         * 医保类型名
         */
        private String medicalCareType = null;

        /**
         * 患者医保编号
         */
        private String medicalInsuranceID = null;

        /**
         * 入院科室名称
         */
        private String category = null;

        /**
         * 入院科室编码
         */
        private String categoryCode = null;

        /**
         * 出院科室名称
         */
        private String leaveCategory = null;

        /**
         * 出院科室编码
         */
        private String leaveCategoryCode = null;

        /**
         * 患者住院号
         */
        private String hospitalNo = null;

        /**
         * 住院就诊编号
         */
        private String visitNo = null;

        /**
         * 就诊日期
         */
        private LocalDate consultationDate = null;

        /**
         * 患者唯一ID
         */
        private String patientId = null;

        /**
         * 患者就诊编号
         */
        private String patientNo = null;

        /**
         * 性别
         */
        private SexEnum sex = null;
    }
}

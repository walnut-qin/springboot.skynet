package com.kaos.skynet.entity.inpatient.fee.balance.invoice.electronic;

import java.time.LocalDateTime;

import com.kaos.skynet.enums.common.SourceTypeEnum;
import com.kaos.skynet.enums.common.TransTypeEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.BusinessTypeEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.StateEnum;
import com.kaos.skynet.enums.inpatient.fee.balance.invoice.electronic.SuccessStateEnum;

import lombok.Data;

/**
 * 电子发票中间表实体
 */
@Data
public class FinComElectronicInvoice {
    /**
     * HIS内发票号
     */
    private String invoiceNo = null;

    /**
     * 电子票据批次号
     */
    private String billBatchCode = null;

    /**
     * 电子发票票据号
     */
    private String billNo = null;

    /**
     * 电子票据系统随机数
     */
    private String random = null;

    /**
     * 电子票据二维码
     */
    private String billQRCode = null;

    /**
     * 电子票据外链
     */
    private String picUrl = null;

    /**
     * 最后操作时间
     */
    private LocalDateTime operDate = null;

    /**
     * 电子发票状态
     */
    private StateEnum state = null;

    /**
     * 电子发票成功与否
     */
    private SuccessStateEnum successState = null;

    /**
     * 交易类型
     */
    private TransTypeEnum transType = null;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType = null;

    /**
     * 业务源
     */
    private SourceTypeEnum sourceType = null;

    /**
     * 卡号
     */
    private String cardNo = null;

    /**
     * 开票员
     */
    private String operCode = null;

    /**
     * 业务流水号(电子票系统唯一)
     */
    private String busNo = null;

    /**
     * 冲红原因
     */
    private String writeOffReason = null;

    /**
     * 创建时间
     */
    private LocalDateTime createDate = null;
}

package com.kaos.skynet.entity.inpatient.fee.balance.invoice.electronic;

import java.time.LocalDateTime;

import com.kaos.skynet.enums.impl.common.SourceTypeEnum;
import com.kaos.skynet.enums.impl.common.TransTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic.BusinessTypeEnum;
import com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic.StateEnum;
import com.kaos.skynet.enums.impl.inpatient.fee.balance.invoice.electronic.SuccessStateEnum;

import lombok.Getter;

/**
 * 电子发票中间表实体
 */
public class FinComElectronicInvoice {
    /**
     * HIS内发票号
     */
    @Getter
    private String invoiceNo = null;

    /**
     * 电子票据批次号
     */
    @Getter
    private String billBatchCode = null;

    /**
     * 电子发票票据号
     */
    @Getter
    private String billNo = null;

    /**
     * 电子票据系统随机数
     */
    @Getter
    private String random = null;

    /**
     * 电子票据二维码
     */
    @Getter
    private String billQRCode = null;

    /**
     * 电子票据外链
     */
    @Getter
    private String picUrl = null;

    /**
     * 最后操作时间
     */
    @Getter
    private LocalDateTime operDate = null;

    /**
     * 电子发票状态
     */
    @Getter
    private StateEnum state = null;

    /**
     * 电子发票成功与否
     */
    @Getter
    private SuccessStateEnum successState = null;

    /**
     * 交易类型
     */
    @Getter
    private TransTypeEnum transType = null;

    /**
     * 业务类型
     */
    @Getter
    private BusinessTypeEnum businessType = null;

    /**
     * 业务源
     */
    @Getter
    private SourceTypeEnum sourceType = null;

    /**
     * 卡号
     */
    @Getter
    private String cardNo = null;

    /**
     * 开票员
     */
    @Getter
    private String operCode = null;

    /**
     * 业务流水号(电子票系统唯一)
     */
    @Getter
    private String busNo = null;

    /**
     * 冲红原因
     */
    @Getter
    private String writeOffReason = null;

    /**
     * 创建时间
     */
    @Getter
    private LocalDateTime createDate = null;
}

package com.kaos.skynet.api.data.entity.inpatient.fee.balance.invoice.electronic;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.enums.common.SourceTypeEnum;
import com.kaos.skynet.api.enums.common.TransTypeEnum;
import com.kaos.skynet.api.enums.inpatient.fee.balance.invoice.electronic.BusinessTypeEnum;
import com.kaos.skynet.api.enums.inpatient.fee.balance.invoice.electronic.StateEnum;
import com.kaos.skynet.api.enums.inpatient.fee.balance.invoice.electronic.SuccessStateEnum;
import com.kaos.skynet.core.type.Entity;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 电子发票中间表实体
 */
@Getter
@Setter
@Builder
public class FinComElectronicInvoice extends Entity {
    /**
     * HIS内发票号
     */
    private String invoiceNo;

    /**
     * 电子票据批次号
     */
    private String billBatchCode;

    /**
     * 电子发票票据号
     */
    private String billNo;

    /**
     * 电子票据系统随机数
     */
    private String random;

    /**
     * 电子票据二维码
     */
    private String billQRCode;

    /**
     * 电子票据外链
     */
    private String picUrl;

    /**
     * 最后操作时间
     */
    private LocalDateTime operDate;

    /**
     * 电子发票状态
     */
    private StateEnum state;

    /**
     * 电子发票成功与否
     */
    private SuccessStateEnum successState;

    /**
     * 交易类型
     */
    private TransTypeEnum transType;

    /**
     * 业务类型
     */
    private BusinessTypeEnum businessType;

    /**
     * 业务源
     */
    private SourceTypeEnum sourceType;

    /**
     * 卡号
     */
    private String cardNo;

    /**
     * 开票员
     */
    private String operCode;

    /**
     * 业务流水号(电子票系统唯一)
     */
    private String busNo;

    /**
     * 冲红原因
     */
    private String writeOffReason;

    /**
     * 创建时间
     */
    private LocalDateTime createDate;

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof FinComElectronicInvoice) {
            var that = (FinComElectronicInvoice) arg0;
            return StringUtils.equals(this.invoiceNo, that.invoiceNo)
                    && this.transType == that.transType;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(invoiceNo, transType);
    }
}

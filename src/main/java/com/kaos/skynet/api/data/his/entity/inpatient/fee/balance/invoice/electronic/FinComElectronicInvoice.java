package com.kaos.skynet.api.data.his.entity.inpatient.fee.balance.invoice.electronic;

import java.time.LocalDateTime;

import com.google.common.base.Objects;
import com.kaos.skynet.api.data.his.enums.SourceTypeEnum;
import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.core.type.Entity;
import com.kaos.skynet.core.type.Enum;
import com.kaos.skynet.core.type.utils.StringUtils;

import lombok.AllArgsConstructor;
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

    /**
     * 电子发票业务类型
     */
    @Getter
    @AllArgsConstructor
    public enum BusinessTypeEnum implements Enum {
        住院("01", "住院"),
        门诊("02", "门诊"),
        急诊("03", "急诊"),
        门特("04", "门特"),
        体检("05", "体检"),
        挂号("06", "挂号"),
        住院预交金("07", "住院预交金"),
        体检预交金("08", "体检预交金"),
        往来票("09", "体检预交金"),
        捐赠票("10", "体检预交金"),
        非税通用票("11", "体检预交金"),
        门诊预交金("12", "体检预交金");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 电子票据状态专用枚举
     */
    @Getter
    @AllArgsConstructor
    public enum StateEnum implements Enum {
        有效("1", "有效"),
        冲红("2", "冲红");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }

    /**
     * 操作状态
     */
    @Getter
    @AllArgsConstructor
    public enum SuccessStateEnum implements Enum {
        失败("0", "失败"),
        成功("1", "成功"),
        补开成功("2", "补开成功"),
        补冲红成功("3", "补冲红成功");

        /**
         * 数据库存值
         */
        private String value;

        /**
         * 描述存值
         */
        private String description;
    }
}

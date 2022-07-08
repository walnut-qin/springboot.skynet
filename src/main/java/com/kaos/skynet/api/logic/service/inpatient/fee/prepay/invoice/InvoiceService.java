package com.kaos.skynet.api.logic.service.inpatient.fee.prepay.invoice;

import com.kaos.skynet.api.data.his.enums.TransTypeEnum;
import com.kaos.skynet.api.data.his.mapper.common.fee.invoice.FinComElectronicInvoiceMapper;
import com.kaos.skynet.plugin.bosoft.BoSoftPlugin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class InvoiceService {
    /**
     * 电子发票接口
     */
    @Autowired
    FinComElectronicInvoiceMapper electronicInvoiceMapper;

    /**
     * 博思接口
     */
    @Autowired
    BoSoftPlugin boSoft;

    /**
     * 修复信息
     */
    @Transactional
    public void fixPrepayElectronicInvoiceInfo(String invoiceNo) {
        // 检索中间表数据
        var data = electronicInvoiceMapper.queryInvoice(invoiceNo, TransTypeEnum.Positive);
        if (data == null) {
            log.error("不存在的电子发票数据, invoiceNo = ".concat(invoiceNo));
            throw new RuntimeException("不存在的电子发票数据");
        }

        // 获取服务器数据
        var rsp = boSoft.postForObject("getEBillByBusNo", new Bosoft.ReqBody(data.getBusNo()), Bosoft.RspBody.class);
        if (rsp == null) {
            log.error("从博思服务器获取电子发票信息失败, invoiceNo = ".concat(invoiceNo));
            throw new RuntimeException("从博思服务器获取电子发票信息失败");
        }

        // 修改信息
        data.setBillBatchCode(rsp.billBatchCode);
        data.setBillNo(rsp.billNo);
        data.setRandom(rsp.random);
        electronicInvoiceMapper.updateInvoice(data);
    }

    /**
     * 接口Body
     */
    static class Bosoft {
        public static class ReqBody {
            /**
             * 业务流水号
             */
            String busNo;

            /**
             * 业务发生时间
             */
            String busDateTime;

            /**
             * 单参数构造
             * 
             * @param busNo
             */
            public ReqBody(String busNo) {
                this.busNo = busNo;
                this.busDateTime = busNo.split("-")[0];
            }
        }

        static class RspBody {
            /**
             * 电子票据代码
             */
            String billBatchCode;

            /**
             * 电子票据号码
             */
            String billNo;

            /**
             * 电子校验码
             */
            String random;
        }
    }
}

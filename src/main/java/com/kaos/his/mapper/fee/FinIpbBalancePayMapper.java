package com.kaos.his.mapper.fee;

import java.util.List;

import com.kaos.his.entity.fee.FinIpbBalancePay;

import org.springframework.stereotype.Service;

@Service
public interface FinIpbBalancePayMapper {
    List<FinIpbBalancePay> QueryBalancePays(String invoiceNo);
}

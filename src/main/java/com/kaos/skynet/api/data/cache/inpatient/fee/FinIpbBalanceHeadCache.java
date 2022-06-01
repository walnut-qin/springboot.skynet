package com.kaos.skynet.api.data.cache.inpatient.fee;

import com.kaos.skynet.api.data.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.skynet.api.data.enums.TransTypeEnum;
import com.kaos.skynet.api.data.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.EqualsAndHashCode;

/**
 * @param 类型 缓存
 * @param 映射 <发票号, 交易类型> -> 手术间信息
 * @param 容量 100
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class FinIpbBalanceHeadCache extends Cache<FinIpbBalanceHeadCache.Key, FinIpbBalanceHead> {
    FinIpbBalanceHeadCache(FinIpbBalanceHeadMapper balanceHeadMapper) {
        super(500, new Converter<Key, FinIpbBalanceHead>() {
            @Override
            public FinIpbBalanceHead convert(Key key) {
                return balanceHeadMapper.queryBalanceHead(key.invoiceNo, key.transType);
            }
        });
    }

    /**
     * 键
     */
    @Builder
    @EqualsAndHashCode
    public static class Key {
        /**
         * 发票号
         */
        private String invoiceNo;

        /**
         * 交易类型
         */
        private TransTypeEnum transType;
    }
}

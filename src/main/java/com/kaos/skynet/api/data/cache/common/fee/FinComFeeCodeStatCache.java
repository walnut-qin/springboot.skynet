package com.kaos.skynet.api.data.cache.common.fee;

import com.kaos.skynet.api.data.entity.common.fee.FinComFeeCodeStat;
import com.kaos.skynet.api.data.entity.common.fee.FinComFeeCodeStat.ReportTypeEnum;
import com.kaos.skynet.api.data.enums.MinFeeEnum;
import com.kaos.skynet.api.data.mapper.common.fee.FinComFeeCodeStatMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Component
public class FinComFeeCodeStatCache extends Cache<FinComFeeCodeStatCache.Key, FinComFeeCodeStat> {
    FinComFeeCodeStatCache(FinComFeeCodeStatMapper feeCodeStatMapper) {
        super(100, new Converter<Key, FinComFeeCodeStat>() {
            @Override
            public FinComFeeCodeStat convert(Key source) {
                return feeCodeStatMapper.queryFeeCodeStat(source.reportType, source.minFee);
            }
        });
    }

    @Builder
    @EqualsAndHashCode
    public static class Key {
        /**
         * 报告类型
         */
        private ReportTypeEnum reportType;

        /**
         * 最小费用编码
         */
        private MinFeeEnum minFee;
    }
}

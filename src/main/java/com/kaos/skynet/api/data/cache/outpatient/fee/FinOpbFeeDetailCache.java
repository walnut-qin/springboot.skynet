package com.kaos.skynet.api.data.cache.outpatient.fee;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import com.kaos.skynet.api.data.entity.outpatient.fee.FinOpbFeeDetail;
import com.kaos.skynet.api.data.mapper.outpatient.fee.FinOpbFeeDetailMapper;
import com.kaos.skynet.core.type.Cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import lombok.Builder;

@Component
public class FinOpbFeeDetailCache extends Cache<FinOpbFeeDetailCache.Key, List<FinOpbFeeDetail>> {
    @Autowired
    FinOpbFeeDetailMapper feeDetailMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(100, new Converter<Key, List<FinOpbFeeDetail>>() {
            @Override
            public List<FinOpbFeeDetail> convert(Key source) {
                var builder = FinOpbFeeDetailMapper.Key.builder();
                builder.cardNo(source.cardNo).itemCode(source.itemCode);
                if (source.offset != null) {
                    builder.operBeginDate(LocalDateTime.now().plusDays(-source.offset))
                            .operEndDate(LocalDateTime.now());
                }
                return feeDetailMapper.queryFeeDetails(builder.build());
            }
        });
    }

    @Builder
    public static class Key {
        /**
         * 就诊卡号
         */
        private String cardNo;

        /**
         * 收费项目
         */
        private String itemCode;

        /**
         * 有效期
         */
        private Integer offset;
    }
}

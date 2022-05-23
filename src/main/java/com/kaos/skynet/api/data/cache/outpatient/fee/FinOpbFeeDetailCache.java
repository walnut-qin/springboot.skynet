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

import lombok.Data;

@Component
public class FinOpbFeeDetailCache extends Cache<FinOpbFeeDetailCache.Key, List<FinOpbFeeDetail>> {
    @Autowired
    FinOpbFeeDetailMapper feeDetailMapper;

    @Override
    @PostConstruct
    protected void postConstruct() {
        super.postConstruct(Key.class, 100, new Converter<Key, List<FinOpbFeeDetail>>() {
            @Override
            public List<FinOpbFeeDetail> convert(Key source) {
                return feeDetailMapper.queryFeeDetails(new FinOpbFeeDetailMapper.Key() {
                    {
                        setCardNo(source.getCardNo());
                        setItemCode(source.getItemCode());
                        if (source.offset != null) {
                            setOperBeginDate(LocalDateTime.now().plusDays(-source.offset));
                            setOperEndDate(LocalDateTime.now());
                        }
                    }
                });
            }
        });
    }

    @Data
    public static class Key {
        /**
         * 就诊卡号
         */
        private String cardNo = null;

        /**
         * 收费项目
         */
        private String itemCode = null;

        /**
         * 有效期
         */
        private Integer offset = null;
    }
}

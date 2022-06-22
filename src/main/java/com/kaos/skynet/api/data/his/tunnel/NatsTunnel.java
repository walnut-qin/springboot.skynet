package com.kaos.skynet.api.data.his.tunnel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.kaos.skynet.api.data.his.mapper.inpatient.escort.annex.EscortAnnexCheckMapper;
import com.kaos.skynet.api.data.his.mapper.pipe.lis.LisResultNewMapper;

import com.kaos.skynet.core.type.Tunnel;
import com.kaos.skynet.core.util.converter.LocalDateTimeToStringConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 核酸检测结果隧道
 */
@Component
public class NatsTunnel implements Tunnel<NatsTunnel.Key, NatsTunnel.Value> {
    @Autowired
    LisResultNewMapper lisResultNewMapper;

    @Autowired
    EscortAnnexCheckMapper escortAnnexCheckMapper;

    @Override
    public Value tunneling(Key key) {
        // 结果整合
        List<Value> result = Lists.newArrayList();
        for (var cardNo : key.cardNos) {
            // 本院核酸结果
            var lisResult = lisResultNewMapper.queryInspectResults(LisResultNewMapper.Key.builder()
                    .patientId(cardNo)
                    .itemCodes(Lists.newArrayList("SARS-CoV-2-RNA"))
                    .beginDate(LocalDateTime.now().minus(key.duration))
                    .build()).stream().map(x -> {
                        return Value.builder()
                                .negative(x.getResult().equals("阴性(-)"))
                                .inspectDate(x.getInspectDate())
                                .build();
                    });
            // 外院核酸结果
            var annexResult = escortAnnexCheckMapper.queryAnnexChecks(EscortAnnexCheckMapper.Key.builder()
                    .cardNo(cardNo)
                    .beginInspectDate(LocalDateTime.now().minus(key.duration))
                    .build()).stream().map(x -> {
                        return Value.builder()
                                .negative(x.getNegative())
                                .inspectDate(x.getInspectDate())
                                .build();
                    });
            result.addAll(Stream.concat(lisResult, annexResult).toList());
        }
        // 排序
        result.sort((x, y) -> {
            return y.inspectDate.compareTo(x.inspectDate);
        });
        // 出结果
        if (result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    @Setter
    @Builder
    public static class Key {
        /**
         * 就诊卡号
         */
        private List<String> cardNos;

        /**
         * 有效期
         */
        private Duration duration;
    }

    @Getter
    @Builder
    public static class Value {
        /**
         * 阴性标识
         */
        private Boolean negative;

        /**
         * 检测时间
         */
        private LocalDateTime inspectDate;

        /**
         * 时间格式化转换器
         */
        private final static LocalDateTimeToStringConverter cvt = new LocalDateTimeToStringConverter(
                "yyyy-MM-dd HH:mm:ss");

        @Override
        public String toString() {
            if (negative) {
                return "阴性(-)".concat(cvt.convert(inspectDate));
            } else {
                return "阳性(-)".concat(cvt.convert(inspectDate));
            }
        }
    }
}

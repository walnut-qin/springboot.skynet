package com.kaos.skynet.api.cache.impl.common.fee;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.common.fee.FinComFeeCodeStat;
import com.kaos.skynet.api.enums.common.MinFeeEnum;
import com.kaos.skynet.api.enums.common.ReportTypeEnum;
import com.kaos.skynet.api.mapper.common.fee.FinComFeeCodeStatMapper;
import com.kaos.skynet.core.gson.Gsons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * 最小费用编码一般不会发生变化，设置为不过期，定期刷新
 * 
 * @param 类型 缓存
 * @param 映射 报表类型 -> {最小费用编码 -> 费用编码}
 * @param 容量 20 x 100
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Log4j
@Component
public class FinComFeeCodeStatCache implements Cache<FinComFeeCodeStatCache.Key, FinComFeeCodeStat> {
    /**
     * 数据库接口
     */
    @Autowired
    FinComFeeCodeStatMapper mapper;

    /**
     * 序列化工具
     */
    Gson gson = Gsons.newGson();

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<FinComFeeCodeStat>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<FinComFeeCodeStat>>() {
                @Override
                public Optional<FinComFeeCodeStat> load(String key) throws Exception {
                    // 反序列化key
                    Key realKey = null;
                    try {
                        realKey = gson.fromJson(key, Key.class);
                    } catch (Exception e) {
                        log.error(String.format("反序列化缓存的key失败(%s)", e.getMessage()));
                    }

                    // 查询响应
                    var refData = mapper.queryFeeCodeStat(realKey.reportType, realKey.minFee);
                    return Optional.fromNullable(refData);
                };
            });

    @Override
    public FinComFeeCodeStat getValue(Key key) {
        try {
            if (key == null) {
                log.warn("键值为空");
                return null;
            } else {
                return this.cache.get(gson.toJson(key)).orNull();
            }
        } catch (Exception e) {
            log.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(Key key) {
        this.cache.refresh(gson.toJson(key));
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.cache.refresh(key);
        }
    }

    @Override
    public View show() {
        View view = new View();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key {
        /**
         * 报告类型
         */
        @Setter
        private ReportTypeEnum reportType = null;

        /**
         * 最小费用编码
         */
        @Setter
        private MinFeeEnum minFee = null;
    }
}

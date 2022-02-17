package com.kaos.his.cache.common;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.enums.common.FeeStatTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.mapper.common.FinComFeeCodeStatMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FinComFeeCodeStatCache implements ICache<Pair<FeeStatTypeEnum, MinFeeEnum>, FinComFeeCodeStat> {
    /**
     * 数据库接口
     */
    @Autowired
    FinComFeeCodeStatMapper mapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(FinComFeeCodeStatCache.class);

    /**
     * Loading cache
     */
    LoadingCache<Pair<FeeStatTypeEnum, MinFeeEnum>, FinComFeeCodeStat> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<Pair<FeeStatTypeEnum, MinFeeEnum>, FinComFeeCodeStat>() {
                @Override
                public FinComFeeCodeStat load(Pair<FeeStatTypeEnum, MinFeeEnum> key) throws Exception {
                    return FinComFeeCodeStatCache.this.mapper.queryFeeCodeStat(key.getValue0(), key.getValue1());
                };
            });

    @Override
    public FinComFeeCodeStat getValue(Pair<FeeStatTypeEnum, MinFeeEnum> key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key);
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public ConcurrentMap<Pair<FeeStatTypeEnum, MinFeeEnum>, FinComFeeCodeStat> show() {
        return this.cache.asMap();
    }

    @Override
    public void refresh(Pair<FeeStatTypeEnum, MinFeeEnum> key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

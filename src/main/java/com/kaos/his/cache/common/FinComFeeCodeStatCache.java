package com.kaos.his.cache.common;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.common.FinComFeeCodeStat;
import com.kaos.his.enums.common.ReportTypeEnum;
import com.kaos.his.enums.common.MinFeeEnum;
import com.kaos.his.mapper.common.FinComFeeCodeStatMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 <报表类型, 最小费用编码> -> 费用编码
 * @param 容量 100
 * @param 刷频 1次/1天
 * @param 过期 永不
 */
@Component
public class FinComFeeCodeStatCache implements ICache<Pair<ReportTypeEnum, MinFeeEnum>, FinComFeeCodeStat> {
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
    LoadingCache<Pair<ReportTypeEnum, MinFeeEnum>, FinComFeeCodeStat> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .recordStats()
            .build(new CacheLoader<Pair<ReportTypeEnum, MinFeeEnum>, FinComFeeCodeStat>() {
                @Override
                public FinComFeeCodeStat load(Pair<ReportTypeEnum, MinFeeEnum> key) throws Exception {
                    return FinComFeeCodeStatCache.this.mapper.queryFeeCodeStat(key.getValue0(), key.getValue1());
                };
            });

    @Override
    public FinComFeeCodeStat getValue(Pair<ReportTypeEnum, MinFeeEnum> key) {
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
    public View<Pair<ReportTypeEnum, MinFeeEnum>, FinComFeeCodeStat> show() {
        View<Pair<ReportTypeEnum, MinFeeEnum>, FinComFeeCodeStat> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void refresh(Pair<ReportTypeEnum, MinFeeEnum> key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

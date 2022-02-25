package com.kaos.his.cache.impl.common.fee;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.kaos.his.cache.Cache;
import com.kaos.his.entity.common.fee.FinComFeeCodeStat;
import com.kaos.his.enums.impl.common.MinFeeEnum;
import com.kaos.his.enums.impl.common.ReportTypeEnum;
import com.kaos.his.mapper.common.FinComFeeCodeStatMapper;

import org.apache.log4j.Logger;
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
public class FinComFeeCodeStatCache implements Cache<ReportTypeEnum, Cache<MinFeeEnum, FinComFeeCodeStat>> {
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
    LoadingCache<ReportTypeEnum, Cache<MinFeeEnum, FinComFeeCodeStat>> cache = CacheBuilder.newBuilder()
            .maximumSize(20)
            .recordStats()
            .build(new CacheLoader<ReportTypeEnum, Cache<MinFeeEnum, FinComFeeCodeStat>>() {
                @Override
                public Cache<MinFeeEnum, FinComFeeCodeStat> load(ReportTypeEnum key1) throws Exception {
                    return new Cache<MinFeeEnum, FinComFeeCodeStat>() {
                        Logger logger = Logger.getLogger(this.getClass());

                        LoadingCache<MinFeeEnum, FinComFeeCodeStat> cache = CacheBuilder.newBuilder()
                                .maximumSize(100)
                                .refreshAfterWrite(1, TimeUnit.DAYS)
                                .build(new CacheLoader<MinFeeEnum, FinComFeeCodeStat>() {
                                    @Override
                                    public FinComFeeCodeStat load(MinFeeEnum key2) throws Exception {
                                        return FinComFeeCodeStatCache.this.mapper.queryFeeCodeStat(key1, key2);
                                    };
                                });

                        @Override
                        public FinComFeeCodeStat getValue(MinFeeEnum key) {
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
                        public void refresh(MinFeeEnum key) {
                            this.cache.refresh(key);
                        }

                        @Override
                        public void refreshAll() {
                            for (var key : this.cache.asMap().keySet()) {
                                this.refresh(key);
                            }
                        }

                        @Override
                        public View<MinFeeEnum, FinComFeeCodeStat> show() {
                            View<MinFeeEnum, FinComFeeCodeStat> view = new View<>();
                            view.size = this.cache.size();
                            view.stats = this.cache.stats();
                            view.cache = this.cache.asMap();
                            return view;
                        }

                        @Override
                        public void invalidateAll() {
                            this.cache.invalidateAll();

                        }
                    };
                };
            });

    @Override
    public Cache<MinFeeEnum, FinComFeeCodeStat> getValue(ReportTypeEnum key) {
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
    public void refresh(ReportTypeEnum key) {
        try {
            this.cache.get(key).refreshAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.refresh(key);
        }
    }

    @Override
    public View<ReportTypeEnum, View<MinFeeEnum, ?>> show() {
        View<ReportTypeEnum, View<MinFeeEnum, ?>> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = Maps.newConcurrentMap();
        for (var key : this.cache.asMap().keySet()) {
            try {
                view.cache.put(key, this.cache.get(key).show());
            } catch (Exception e) {
                this.logger.warn(e.getMessage());
                continue;
            }
        }
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

package com.kaos.skynet.api.cache.impl.inpatient.fee.balance;

import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.entity.inpatient.fee.balance.FinIpbBalanceHead;
import com.kaos.skynet.api.mapper.inpatient.fee.balance.FinIpbBalanceHeadMapper;
import com.kaos.skynet.core.gson.Gsons;
import com.kaos.skynet.enums.common.TransTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

/**
 * @param 类型 缓存
 * @param 映射 <发票号, 交易类型> -> 手术间信息
 * @param 容量 100
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Log4j
@Component
public class FinIpbBalanceHeadCache implements Cache<FinIpbBalanceHeadCache.Key, FinIpbBalanceHead> {
    /**
     * 数据库接口
     */
    @Autowired
    FinIpbBalanceHeadMapper balanceHeadMapper;

    /**
     * 序列化工具
     */
    Gson gson = Gsons.newGson();

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<FinIpbBalanceHead>> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<FinIpbBalanceHead>>() {
                @Override
                public Optional<FinIpbBalanceHead> load(String key) throws Exception {
                    // 反序列化key
                    Key realKey = null;
                    try {
                        realKey = gson.fromJson(key, Key.class);
                    } catch (Exception e) {
                        log.error(String.format("反序列化缓存索引失败(%s)", key));
                    }

                    // 检索数据库
                    var ref = balanceHeadMapper.queryBalance(realKey.invoiceNo, realKey.transType);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public FinIpbBalanceHead getValue(Key key) {
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

    /**
     * 键
     */
    public static class Key {
        /**
         * 发票号
         */
        @Setter
        private String invoiceNo = null;

        /**
         * 交易类型
         */
        @Setter
        private TransTypeEnum transType = null;
    }
}

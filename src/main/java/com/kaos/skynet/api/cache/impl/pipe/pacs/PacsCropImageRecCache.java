package com.kaos.skynet.api.cache.impl.pipe.pacs;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.pipe.pacs.PacsCropImageRecMapper;
import com.kaos.skynet.entity.pipe.pacs.PacsCropImageRec;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 映射号 -> 切割记录
 * @param 容量 100
 * @param 刷频 无刷
 * @param 过期 永不
 */
@Component
public class PacsCropImageRecCache implements Cache<String, PacsCropImageRec> {
    /**
     * 数据库接口
     */
    @Autowired
    PacsCropImageRecMapper cropImageRecMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(PacsCropImageRecCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<PacsCropImageRec>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, Optional<PacsCropImageRec>>() {
                @Override
                public Optional<PacsCropImageRec> load(String key) throws Exception {
                    // 检索记录
                    var ref = PacsCropImageRecCache.this.cropImageRecMapper.queryRecWithRefer(key);
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public PacsCropImageRec getValue(String key) {
        try {
            if (key == null) {
                this.logger.warn("键值为空");
                return null;
            } else {
                return this.cache.get(key).orNull();
            }
        } catch (Exception e) {
            this.logger.warn(e.getMessage());
            return null;
        }
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void refreshAll() {
        for (var key : this.cache.asMap().keySet()) {
            this.refresh(key);
        }
    }

    @Override
    public View<String, Optional<PacsCropImageRec>> show() {
        View<String, Optional<PacsCropImageRec>> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

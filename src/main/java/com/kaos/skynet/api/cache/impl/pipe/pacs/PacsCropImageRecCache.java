package com.kaos.skynet.api.cache.impl.pipe.pacs;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.pipe.pacs.PacsCropImageRecMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.coobird.thumbnailator.Thumbnails;

import java.awt.image.BufferedImage;

/**
 * @param 类型 缓存
 * @param 映射 映射号 -> 缓存图片
 * @param 容量 100
 * @param 刷频 无刷
 * @param 过期 永不
 */
@Component
public class PacsCropImageRecCache implements Cache<String, BufferedImage> {
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
    LoadingCache<String, Optional<BufferedImage>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .recordStats()
            .build(new CacheLoader<String, Optional<BufferedImage>>() {
                @Override
                public Optional<BufferedImage> load(String key) throws Exception {
                    // 检索记录
                    var rec = PacsCropImageRecCache.this.cropImageRecMapper.queryRecWithRefer(key);
                    if (rec == null) {
                        return Optional.absent();
                    }
                    // 构造缓存内容
                    var ref = Thumbnails.of(rec.url)
                            .scale(1f)
                            .sourceRegion(rec.x, rec.y, rec.w, rec.h)
                            .asBufferedImage();
                    return Optional.fromNullable(ref);
                };
            });

    @Override
    public BufferedImage getValue(String key) {
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
    public View<String, Optional<BufferedImage>> show() {
        View<String, Optional<BufferedImage>> view = new View<>();
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

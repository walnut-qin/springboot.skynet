package com.kaos.his.cache.inpatient.surgery;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.his.entity.inpatient.surgery.MetOpsRoom;
import com.kaos.his.mapper.inpatient.surgery.MetOpsRoomMapper;
import com.kaos.inf.ICache;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetOpsRoomCache implements ICache<String, MetOpsRoom> {
    /**
     * 数据库接口
     */
    @Autowired
    MetOpsRoomMapper metOpsRoomMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(MetOpsRoomCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, MetOpsRoom> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .refreshAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, MetOpsRoom>() {
                @Override
                public MetOpsRoom load(String key) throws Exception {
                    return MetOpsRoomCache.this.metOpsRoomMapper.queryMetOpsRoom(key);
                };
            });

    @Override
    public MetOpsRoom getValue(String key) {
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
    public View<String, MetOpsRoom> show() {
        View<String, MetOpsRoom> view = new View<>();
        view.size = this.cache.size();
        view.stats = this.cache.stats();
        view.cache = this.cache.asMap();
        return view;
    }

    @Override
    public void refresh(String key) {
        this.cache.refresh(key);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }
}

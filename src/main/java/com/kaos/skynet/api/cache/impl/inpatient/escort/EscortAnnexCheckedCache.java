package com.kaos.skynet.api.cache.impl.inpatient.escort;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexChkMapper;
import com.kaos.skynet.api.mapper.inpatient.escort.EscortAnnexInfoMapper;
import com.kaos.skynet.entity.inpatient.escort.EscortAnnexInfo;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @param 类型 缓存
 * @param 映射 患者卡号 -> 附件信息(附带审核信息)
 * @param 容量 500
 * @param 刷频 无刷
 * @param 过期 5sec
 */
@Component
public class EscortAnnexCheckedCache implements Cache<String, EscortAnnexInfo> {
    /**
     * 信息接口
     */
    @Autowired
    EscortAnnexInfoMapper infoMapper;

    /**
     * 审核接口
     */
    @Autowired
    EscortAnnexChkMapper checkMapper;

    /**
     * 日志接口
     */
    Logger logger = Logger.getLogger(EscortAnnexCheckedCache.class);

    /**
     * Loading cache
     */
    LoadingCache<String, Optional<EscortAnnexInfo>> cache = CacheBuilder.newBuilder()
            .maximumSize(500)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .recordStats()
            .build(new CacheLoader<String, Optional<EscortAnnexInfo>>() {
                @Override
                public Optional<EscortAnnexInfo> load(String key) throws Exception {
                    var rets = EscortAnnexCheckedCache.this.infoMapper.queryAnnexInfos(key, null, null, true);
                    rets.forEach((x) -> {
                        x.associateEntity.escortAnnexChk = EscortAnnexCheckedCache.this.checkMapper
                                .queryAnnexChk(x.annexNo);
                    });
                    rets.removeIf((x) -> {
                        return x.associateEntity.escortAnnexChk == null;
                    });
                    if (rets != null && !rets.isEmpty()) {
                        Collections.sort(rets, (x, y) -> {
                            var k1 = x.associateEntity.escortAnnexChk.inspectDate;
                            var k2 = y.associateEntity.escortAnnexChk.inspectDate;
                            return k2.compareTo(k1);
                        });
                        return Optional.fromNullable(rets.get(0));
                    }
                    return Optional.absent();
                };
            });

    @Override
    public EscortAnnexInfo getValue(String key) {
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
}

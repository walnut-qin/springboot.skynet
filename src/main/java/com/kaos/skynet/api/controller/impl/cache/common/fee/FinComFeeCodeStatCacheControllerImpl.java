package com.kaos.skynet.api.controller.impl.cache.common.fee;

import com.kaos.skynet.api.cache.Cache;
import com.kaos.skynet.api.cache.Cache.View;
import com.kaos.skynet.api.cache.impl.common.fee.FinComFeeCodeStatCache.Key;
import com.kaos.skynet.api.controller.inf.cache.CacheController;
import com.kaos.skynet.entity.common.fee.FinComFeeCodeStat;
import com.kaos.skynet.enums.impl.common.MinFeeEnum;
import com.kaos.skynet.enums.impl.common.ReportTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/ms/cache/common/fee/code")
public class FinComFeeCodeStatCacheControllerImpl implements CacheController<Key, FinComFeeCodeStat> {
    /**
     * 实体信息服务
     */
    @Autowired
    Cache<ReportTypeEnum, Cache<MinFeeEnum, FinComFeeCodeStat>> feeCodeStatCache;

    @Override
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public View show() {
        return this.feeCodeStatCache.show();
    }

    @Override
    @RequestMapping(value = "clear", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
    public String clear() {
        this.feeCodeStatCache.invalidateAll();
        return "清空缓存成功";
    }
}

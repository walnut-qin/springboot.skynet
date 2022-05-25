package com.kaos.skynet.api.logic.controller.cache;

import java.util.Map;

import com.kaos.skynet.api.data.cache.DataCache;
import com.kaos.skynet.api.logic.controller.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
@Validated
@RestController
@RequestMapping("/api/cache")
public class CacheController {
    @Autowired
    DataCache dataCache;

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @RequestMapping(value = "show", method = RequestMethod.GET, produces = MediaType.JSON)
    public Map<String, Object> show() {
        // 入参记录
        log.info("展示系统缓存日志");

        // 构造响应体
        return dataCache.showCacheLog();
    }
}

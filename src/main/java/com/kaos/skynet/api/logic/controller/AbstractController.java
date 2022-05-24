package com.kaos.skynet.api.logic.controller;

import com.kaos.skynet.api.data.cache.common.config.ConfigMapCache;
import com.kaos.skynet.api.data.cache.common.config.ConfigMultiMapCache;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 抽象控制器，管理所有数据接口
 */
public abstract class AbstractController {
    @Autowired
    ConfigMapCache configMapCache;

    @Autowired
    ConfigMultiMapCache configMultiMapCache;
}

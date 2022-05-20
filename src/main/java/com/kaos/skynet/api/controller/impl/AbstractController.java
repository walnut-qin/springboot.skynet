package com.kaos.skynet.api.controller.impl;

import com.google.gson.Gson;
import com.kaos.skynet.core.Gsons;

/**
 * 基础接口，封装一些基本对象
 */
public abstract class AbstractController {
    /**
     * gson工具
     */
    protected Gson gson = Gsons.newGson();
}

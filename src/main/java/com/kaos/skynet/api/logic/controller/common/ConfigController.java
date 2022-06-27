package com.kaos.skynet.api.logic.controller.common;

import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.data.his.cache.common.config.ConfigSwitchCache;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@PassToken
@Validated
@RestController
@RequestMapping("api/common/config")
class ConfigController {
    /**
     * 开关缓存
     */
    @Autowired
    ConfigSwitchCache configSwitchCache;

    /**
     * 检索开关变量的值
     * 
     * @param switchName
     * @return
     */
    @RequestMapping(value = "queryState", method = RequestMethod.POST, produces = MediaType.JSON)
    Boolean queryState(@NotBlank(message = "开关名不能为空") String switchName) {
        // 获取开关的值
        var swt = this.configSwitchCache.get(switchName);
        if (swt == null || swt.getValid() != ValidEnum.VALID) {
            throw new RuntimeException("未找到开关");
        }

        return swt.getValue();
    }
}

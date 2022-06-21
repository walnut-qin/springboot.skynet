package com.kaos.skynet.api.logic.controller.common;

import javax.validation.constraints.NotBlank;

import com.kaos.skynet.api.data.his.cache.common.config.ConfigSwitchCache;
import com.kaos.skynet.api.data.his.enums.ValidEnum;
import com.kaos.skynet.api.logic.controller.MediaType;
import com.kaos.skynet.core.spring.converter.JsonWrappedHttpMessageConverter.RspWrapper;

import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j;

@Log4j
@Validated
@RestController
@RequestMapping({ "api/common/config", "/ms/common/config" })
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
    @RequestMapping(value = "queryState", method = RequestMethod.GET, produces = MediaType.JSON)
    RspWrapper<Boolean> queryState(@NotBlank(message = "开关名不能为空") String switchName) {
        try {
            // 记录日志
            log.info(String.format("查询开关变量(key = %s)", switchName));

            // 获取开关的值
            var swt = this.configSwitchCache.get(switchName);
            if (swt == null || swt.getValid() != ValidEnum.VALID) {
                throw new NotFoundException("未找到开关");
            }

            return RspWrapper.wrapSuccessResponse(swt.getValue());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }
}

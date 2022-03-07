package com.kaos.skynet.api.controller.inf.common.config;

import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

@Validated
public interface SwitchController {
    /**
     * 查询开关状态
     * 
     * @param switchName 开关名称
     * @return
     */
    Boolean queryState(@NotBlank(message = "开关名不能为空") String switchName);
}

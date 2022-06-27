package com.kaos.skynet.core.api.logic.controller;

import java.time.Duration;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.core.api.logic.service.TokenService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@Validated
@RestController
@RequestMapping("/api/user")
public class UserController {
    /**
     * token操作业务
     */
    @Autowired
    TokenService tokenService;

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @PassToken
    @ApiName("登陆系统")
    @RequestMapping(value = "login", method = RequestMethod.POST, produces = MediaType.JSON)
    Login.RspBody login(@RequestBody @Valid Login.ReqBody reqBody) {
        // 校验并生成token
        String token = null;
        if (reqBody.eternal != null && reqBody.eternal) {
            token = tokenService.genToken(reqBody.uuid, reqBody.pwd, null);
        } else {
            token = tokenService.genToken(reqBody.uuid, reqBody.pwd, Duration.ofHours(1));
        }

        // 生成响应
        var builder = Login.RspBody.builder();
        builder.uid(reqBody.uuid);
        builder.token(token);
        return builder.build();
    }

    static class Login {
        static class ReqBody {
            /**
             * 用户ID
             */
            @NotBlank(message = "账号不能为空")
            String uuid;

            /**
             * 用户密码
             */
            @NotBlank(message = "密码不能为空")
            String pwd;

            /**
             * 是否永久登入
             */
            Boolean eternal;
        }

        @Builder
        static class RspBody {
            /**
             * 用户ID
             */
            String uid;

            /**
             * 用户密码
             */
            String token;
        }
    }
}

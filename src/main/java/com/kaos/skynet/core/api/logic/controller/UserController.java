package com.kaos.skynet.core.api.logic.controller;

import javax.validation.Valid;

import com.kaos.skynet.core.api.logic.service.TokenService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.config.spring.net.RspWrapper;

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
    @RequestMapping(value = "login", method = RequestMethod.GET, produces = MediaType.JSON)
    RspWrapper<Login.RspBody> login(@RequestBody @Valid Login.ReqBody reqBody) {
        try {
            // 校验并生成token
            String token = tokenService.genToken(reqBody.uuid, reqBody.pwd);

            // 生成响应
            var builder = Login.RspBody.builder();
            builder.uid(reqBody.uuid);
            builder.token(token);
            return RspWrapper.wrapSuccessResponse(builder.build());
        } catch (Exception e) {
            return RspWrapper.wrapFailResponse(e.getMessage());
        }
    }

    static class Login {
        static class ReqBody {
            /**
             * 用户ID
             */
            String uuid;

            /**
             * 用户密码
             */
            String pwd;
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

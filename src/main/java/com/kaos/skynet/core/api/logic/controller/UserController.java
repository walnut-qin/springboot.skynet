package com.kaos.skynet.core.api.logic.controller;

import java.time.Duration;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import com.kaos.skynet.core.api.data.mapper.KaosUserRoleMapper;
import com.kaos.skynet.core.api.logic.service.TokenService;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.config.spring.net.MediaType;
import com.kaos.skynet.core.util.UserUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;

@CrossOrigin
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
     * 用户角色
     */
    @Autowired
    KaosUserRoleMapper kaosUserRoleMapper;

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
        String token = tokenService.genToken(reqBody.userCode, reqBody.password, Duration.ofHours(1));

        // 生成响应
        var builder = Login.RspBody.builder();
        builder.token(token);
        return builder.build();
    }

    static class Login {
        static class ReqBody {
            /**
             * 用户ID
             */
            @NotBlank(message = "账号不能为空")
            String userCode;

            /**
             * 用户密码
             */
            @NotBlank(message = "密码不能为空")
            String password;
        }

        @Builder
        static class RspBody {
            /**
             * 用户密码
             */
            String token;
        }
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @ApiName("获取用户信息")
    @RequestMapping(value = "info", method = RequestMethod.GET, produces = MediaType.JSON)
    Info.RspBody info() {
        // 获取登入账号信息
        var kaosUser = UserUtils.currentUser();

        // 检索角色信息
        var keyBuilder = KaosUserRoleMapper.Key.builder();
        keyBuilder.userCode(kaosUser.getUserCode());
        var kaosUserRoles = kaosUserRoleMapper.queryKaosUserRoles(keyBuilder.build());

        // 构造响应
        var rspBuilder = Info.RspBody.builder();
        rspBuilder.name(kaosUser.getUserName());
        rspBuilder.roles(kaosUserRoles.stream().map(x -> {
            return x.getRole();
        }).toList());
        return rspBuilder.build();
    }

    static class Info {
        @Builder
        static class RspBody {
            /**
             * 用户密码
             */
            String name;

            /**
             * 角色
             */
            List<String> roles;
        }
    }

    /**
     * 展示系统缓存统计信息
     * 
     * @return
     */
    @ApiName("注销")
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    void logout() {
        // do nothing
    }
}

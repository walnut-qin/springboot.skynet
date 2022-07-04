package com.kaos.skynet.core.api.logic.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kaos.skynet.core.api.data.entity.KaosUser;
import com.kaos.skynet.core.api.data.entity.KaosUserAccess;
import com.kaos.skynet.core.api.data.mapper.KaosUserAccessMapper;
import com.kaos.skynet.core.api.data.mapper.KaosUserMapper;
import com.kaos.skynet.core.config.spring.exception.TokenCheckException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class TokenService {
    /**
     * 账户信息表
     */
    @Autowired
    KaosUserMapper kaosUserMapper;

    /**
     * 密码信息接口
     */
    @Autowired
    KaosUserAccessMapper kaosUserAccessMapper;

    /**
     * 秘钥前缀 - 修改后，系统所有历史token失效
     */
    final String tokenPrefix = "kaos";

    /**
     * 默认时区
     */
    final ZoneId defaultZoneId = ZoneId.systemDefault();

    /**
     * token安全时段 - token生成后这个时段内时，认为是安全的
     */
    final Duration safeDuration = Duration.ofHours(2);

    /**
     * token预警时段 - 预留该时段用于刷新token
     */
    final Duration alertDuration = Duration.ofHours(2);

    /**
     * 生成token
     * 
     * @param kaosUserAccess
     * @return
     */
    private String genToken(@NotNull(message = "账户接入信息不能为空") KaosUserAccess kaosUserAccess) {
        var builder = JWT.create();
        LocalDateTime now = LocalDateTime.now();
        builder.withKeyId(now.toString()); // 混淆Header段
        builder.withAudience(kaosUserAccess.getUserCode(), now.toString()); // 插入用户数据和时间段混淆
        builder.withExpiresAt(now.plus(safeDuration).plus(alertDuration).atZone(defaultZoneId).toInstant());
        String tokenPrivateKey = tokenPrefix + kaosUserAccess.getTokenMask() + kaosUserAccess.getPassword();
        return builder.sign(Algorithm.HMAC256(tokenPrivateKey));
    }

    /**
     * 校验用户并生成token
     * 
     * @param uid      用户ID
     * @param pwd      用户密码
     * @param duration token有效期
     * @return
     */
    @Transactional
    public String genToken(String userCode, String password) {
        // 检索账户实体
        KaosUser kaosUser = kaosUserMapper.queryKaosUser(userCode);
        if (kaosUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检索接入信息
        KaosUserAccess kaosUserAccess = kaosUserAccessMapper.queryKaosUserAccess(userCode);
        if (kaosUserAccess == null) {
            throw new RuntimeException("用户信息异常, 接入信息不存在");
        }

        // 校验密码
        String md5Pwd = DigestUtils.md5DigestAsHex(password.getBytes()).toUpperCase();
        if (!md5Pwd.equals(kaosUserAccess.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 生成token
        return genToken(kaosUserAccess);
    }

    /**
     * 校验token
     * 
     * @param token 原始token
     * @return 账号信息
     * @throws TokenCheckException
     */
    @Transactional
    public KaosUser checkToken(String token, HttpServletResponse response) throws TokenCheckException {
        // 无token
        if (token == null) {
            throw new TokenCheckException("无token, 请登录");
        }

        // token解码
        DecodedJWT decodedJWT = null;
        try {
            decodedJWT = JWT.decode(token);
        } catch (Exception e) {
            throw new TokenCheckException(e.getMessage());
        }

        // 获取系统用户
        String userCode = decodedJWT.getAudience().get(0);

        // 检索账户实体
        KaosUser kaosUser = kaosUserMapper.queryKaosUser(userCode);
        if (kaosUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 获取接入信息
        KaosUserAccess kaosUserAccess = kaosUserAccessMapper.queryKaosUserAccess(userCode);
        if (kaosUserAccess == null) {
            throw new RuntimeException("用户信息异常, 接入信息不存在");
        }

        // 过期校验
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expire = LocalDateTime.ofInstant(decodedJWT.getExpiresAtAsInstant(), ZoneId.systemDefault());
        LocalDateTime alert = expire.minus(alertDuration);
        if (now.isAfter(expire)) {
            throw new TokenCheckException(1, "token已过期");
        } else if (now.isAfter(alert)) {
            response.setHeader("Token", genToken(kaosUserAccess));
        }

        // 校验token
        String tokenPrivateKey = tokenPrefix + kaosUserAccess.getTokenMask() + kaosUserAccess.getPassword();
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(tokenPrivateKey)).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new TokenCheckException("token校验失败");
        }

        return kaosUser;
    }
}

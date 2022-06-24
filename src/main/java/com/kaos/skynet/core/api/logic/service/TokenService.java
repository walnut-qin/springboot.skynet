package com.kaos.skynet.core.api.logic.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kaos.skynet.core.api.data.entity.KaosUser;
import com.kaos.skynet.core.api.data.mapper.KaosUserMapper;
import com.kaos.skynet.core.config.spring.exception.TokenCheckException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class TokenService {
    /**
     * 账户信息表
     */
    @Autowired
    KaosUserMapper kaosUserMapper;

    /**
     * 秘钥前缀 - 修改此前缀可以使所有永久秘钥失效
     */
    final String keyPrefix = "kaos";

    /**
     * 校验用户并生成token
     * 
     * @param uid      用户ID
     * @param pwd      用户密码
     * @param duration token有效期
     * @return
     */
    @Transactional
    public String genToken(String uuid, String pwd, Duration duration) {
        // 检索账户实体
        KaosUser kaosUser = kaosUserMapper.queryKaosUser(uuid);
        if (kaosUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 校验密码
        String md5Pwd = DigestUtils.md5DigestAsHex(pwd.getBytes()).toUpperCase();
        if (!md5Pwd.equals(kaosUser.getPwd())) {
            throw new RuntimeException("密码错误");
        }

        // 生成token
        var builder = JWT.create();
        builder.withAudience(uuid);
        if (duration != null) {
            builder.withExpiresAt(LocalDateTime.now().plus(duration).atZone(ZoneId.systemDefault()).toInstant());
        }
        return builder.sign(Algorithm.HMAC256(keyPrefix + kaosUser.getPwd()));
    }

    /**
     * 校验token
     * 
     * @param token 原始token
     * @return 账号信息
     * @throws TokenCheckException
     */
    @Transactional
    public KaosUser checkToken(String token) throws TokenCheckException {
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

        // 过期校验
        var expireInstant = decodedJWT.getExpiresAtAsInstant();
        if (expireInstant != null) {
            LocalDateTime expire = LocalDateTime.ofInstant(expireInstant, ZoneId.systemDefault());
            if (LocalDateTime.now().isAfter(expire)) {
                throw new TokenCheckException(1, "token已过期");
            }
        }

        // 获取系统用户
        String uuid = decodedJWT.getAudience().get(0);
        KaosUser kaosUser = kaosUserMapper.queryKaosUser(uuid);
        if (kaosUser == null) {
            throw new TokenCheckException("用户不存在, 请重新登录");
        }

        // 校验token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(keyPrefix + kaosUser.getPwd())).build();
        try {
            jwtVerifier.verify(token);
        } catch (Exception e) {
            throw new TokenCheckException("token校验失败");
        }

        return kaosUser;
    }
}

package com.kaos.skynet.core.config.spring.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kaos.skynet.core.api.logic.service.TokenService;
import com.kaos.skynet.core.config.spring.exception.TokenCheckException;
import com.kaos.skynet.core.config.spring.interceptor.annotation.ApiName;
import com.kaos.skynet.core.config.spring.interceptor.annotation.PassToken;
import com.kaos.skynet.core.util.Timer;
import com.kaos.skynet.core.util.json.GsonWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.Cleanup;
import lombok.extern.log4j.Log4j;

/**
 * 拦截器配置
 */
@Configuration
class InterceptorConfigurer implements WebMvcConfigurer {
    /**
     * 序列化工具
     */
    GsonWrapper gsonWrapper = new GsonWrapper();

    /**
     * 核心数据库 - 账户信息接口
     */
    @Autowired
    TokenService tokenService;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/api/**");
        registry.addInterceptor(new TimerInterceptor()).addPathPatterns("/api/**");

        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /**
     * token校验
     */
    class TokenInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            // 获取方法
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();

            // 若方法含有Pass注解，跳过校验
            if (method.isAnnotationPresent(PassToken.class)) {
                return true;
            }

            // 若类上有Pass注解，也跳过校验
            if (method.getDeclaringClass().isAnnotationPresent(PassToken.class)) {
                return true;
            }

            // 读取token头
            String token = request.getHeader("token");
            if (token == null) {
                throw new TokenCheckException("无token, 请登录");
            }

            // 校验token
            tokenService.checkToken(token);

            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) throws Exception {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                Exception ex)
                throws Exception {
        }
    }

    /**
     * 日志记录
     */
    @Log4j
    class LogInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            // 仅预处理HandlerMethod
            if (!(handler instanceof HandlerMethod)) {
                return true;
            }

            // 仅对POST方法自动写日志
            if (!request.getMethod().equals("POST")) {
                return true;
            }

            // 对注解了ApiName的方法改名
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            String apiName = "匿名";
            if (method.isAnnotationPresent(ApiName.class)) {
                // 提取注解内容
                ApiName apiNameAnnotation = method.getAnnotation(ApiName.class);
                apiName = apiNameAnnotation.value();
            }

            // 提取body内容并简化
            @Cleanup
            var InputStream = request.getInputStream();
            String orgBodyStr = new String(StreamUtils.copyToByteArray(InputStream), request.getCharacterEncoding());

            // 记录日志
            log.info(apiName.concat(", reqBody = ").concat(gsonWrapper.format(orgBodyStr)));

            return true;
        }
    }

    /**
     * 计时器生命期管理
     */
    class TimerInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            // 创建计时器
            Timer.create();

            // 放行
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                ModelAndView modelAndView) throws Exception {
            // 销毁计时器
            Timer.destroy();
        }
    }
}

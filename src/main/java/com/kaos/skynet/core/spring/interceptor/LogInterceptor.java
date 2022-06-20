package com.kaos.skynet.core.spring.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.log4j.Log4j;

@Log4j
public class LogInterceptor implements HandlerInterceptor {
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

        // 仅对注解了ApiName的方法自动写日志
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(ApiName.class)) {
            return true;
        }

        // 提取注解内容
        ApiName apiName = method.getAnnotation(ApiName.class);
        String api = apiName.value();

        // 提取body内容
        byte[] bodyBytes = StreamUtils.copyToByteArray(request.getInputStream());
        String body = new String(bodyBytes, request.getCharacterEncoding());

        // 记录日志
        log.info(api.concat(body.replaceAll("(\r\n|\r|\n|    )", "")));

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    }

    /**
     * 日志处理配套的注解
     */
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface ApiName {
        String value();
    }
}

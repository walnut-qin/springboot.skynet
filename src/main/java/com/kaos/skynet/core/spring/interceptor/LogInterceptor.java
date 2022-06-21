package com.kaos.skynet.core.spring.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.Cleanup;
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
        new String();
        String body = new Gson().toJson(JsonParser.parseString(orgBodyStr));

        // 记录日志
        log.info(apiName.concat(", reqBody = ").concat(body));

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

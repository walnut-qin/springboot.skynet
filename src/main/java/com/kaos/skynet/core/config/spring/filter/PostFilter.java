package com.kaos.skynet.core.config.spring.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import lombok.Cleanup;

/**
 * 该过滤器针对HTTP的POST请求过滤，将request作持久化处理，让body可以重复读
 */
@Component
@WebFilter(urlPatterns = "/**")
class PostFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 仅对HttpServletRequest请求作持久化处理
        if (!(request instanceof HttpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // 仅对POST方法作持久化处理
        if (!httpServletRequest.getMethod().equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // 持久化处理
        chain.doFilter(new EternalHttpServletRequestWrapper(httpServletRequest), response);
    }

    /**
     * 内部定义一个请求包装器，持久存储请求的body
     */
    static class EternalHttpServletRequestWrapper extends HttpServletRequestWrapper {
        /**
         * 永久存储请求的body
         */
        private byte[] body;

        /**
         * 构造函数，存储body
         * 
         * @param request
         * @throws IOException
         */
        public EternalHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
            // 拷贝请求信息
            super(request);

            // 获取输入流
            @Cleanup
            var orgInputStream = request.getInputStream();

            // 缓存body
            body = StreamUtils.copyToByteArray(orgInputStream);
        }

        /**
         * 重新包装输入流
         */
        @Override
        public ServletInputStream getInputStream() throws IOException {
            // 拷贝存储之前保存的body
            InputStream bodyStream = new ByteArrayInputStream(body);

            // 构造新的输入流
            return new ServletInputStream() {
                @Override
                public int read() throws IOException {
                    return bodyStream.read();
                }

                @Override
                public boolean isFinished() {
                    return false;
                }

                @Override
                public boolean isReady() {
                    return false;
                }

                @Override
                public void setReadListener(ReadListener listener) {
                }

                @Override
                public void close() throws IOException {
                    bodyStream.close();
                    super.close();
                }
            };
        }

        @Override
        public BufferedReader getReader() throws IOException {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }
    }
}

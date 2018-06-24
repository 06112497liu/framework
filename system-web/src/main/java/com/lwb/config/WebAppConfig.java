package com.lwb.config;

import com.lwb.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author liuweibo
 * @date 2018/6/22
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    /**
     * 添加自定义拦截器（防止表单重复提交的拦截器）
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor())
            .addPathPatterns("/**");
    }

}

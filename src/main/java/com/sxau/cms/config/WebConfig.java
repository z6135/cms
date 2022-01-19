package com.sxau.cms.config;

import com.sxau.cms.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ProjectName: cms
 * @Package: com.sxau.cms.config
 * @ClassName: WebConfig
 * @Author: 张晟睿
 * @Date: 2022/1/10 16:33
 * @Version: 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 添加jwt拦截器,并指定拦截路径
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/auth/**");
    }
    /**
     * jwt拦截器
     */
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}

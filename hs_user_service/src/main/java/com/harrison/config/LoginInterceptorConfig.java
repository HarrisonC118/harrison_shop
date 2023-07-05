package com.harrison.config;

import com.harrison.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LoginInterceptorConfig implements WebMvcConfigurer {
    /**
     * 配置拦截器
     *
     * @param registry 相当于拦截器的注册中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//       下面这句代码相当于添加一个拦截器   添加的拦截器就是我们刚刚创建的
        registry.addInterceptor(new LoginInterceptor())
//       addPathPatterns()配置我们要拦截哪些路径 addPathPatterns("/**")表示拦截所有请求，包括我们的静态资源
                .addPathPatterns("/**")
//       excludePathPatterns()表示我们要放行哪些（表示不用经过拦截器）
//       excludePathPatterns("/","/login")表示放行“/”与“/login”请求
//       如果有静态资源的时候可以在这个地方放行
                .excludePathPatterns("/api/user/v1/login",
                        "/api/user/v1/register",
                        "/api/user/v1/upload_head_portrait",
                        "/api/notify/v1/**"
                );
    }
}

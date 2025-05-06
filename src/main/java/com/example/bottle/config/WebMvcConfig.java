package com.example.bottle.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC配置类
 * 主要配置静态资源访问路径
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String uploadDir;

    /**
     * 配置静态资源处理器
     * @param registry 资源处理器注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将/uploads/**映射到文件上传目录
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadDir + "/");
    }
}
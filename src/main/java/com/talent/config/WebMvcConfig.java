package com.talent.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 配置要允許跨來源訪問的 API 端點
                .allowedOrigins("http://localhost:5173") // 允許跨來源請求的來源，可以使用 "*" 表示所有來源
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允許的 HTTP 方法
                .allowedHeaders("Content-Type", "Authorization") // 允許的 HTTP headers
                .allowCredentials(true); // 是否允許包含認證資訊，如 cookie

        // 可以添加多個 addMapping 來設置不同的 API 端點的 CORS 設置
    }
}


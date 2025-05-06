package com.example.bottle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.bottle.mapper")
public class BottleApplication {
    public static void main(String[] args) {
        SpringApplication.run(BottleApplication.class, args);
    }
} 
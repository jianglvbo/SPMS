package com.mieo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mieo.mapper")
public class SpmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpmsApplication.class);
    }
}

package com.mieo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.mieo.mapper")
public class SpmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpmsApplication.class);
    }
}

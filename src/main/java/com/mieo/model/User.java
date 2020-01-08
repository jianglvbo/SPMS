package com.mieo.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

@Data
@Component
@Alias("user")
public class User {
    private String username;
    private String password;
}

package com.mieo.service;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface LoginService {
    /**
     * 查询账户信息
     * @return 数据库user表中所有信息
     */
    List<Map<String,String>> queryUserInfo();
}

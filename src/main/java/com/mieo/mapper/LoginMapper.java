package com.mieo.mapper;

import com.mieo.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface LoginMapper {
    /**
     * 查询账户信息
     * @return 数据库user表中所有信息
     */
    List<User> queryUserInfo();
}

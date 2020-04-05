package com.mieo.common.util;

import com.mieo.model.util.MyType;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class Encipher {
    /**
     * 对密码进行加密
     *
     * @param password 需要加密的密码
     * @return 密码和盐
     */
    public Map<String, String> encypt(String password) {
        //获取盐
        String salt = UUID.randomUUID().toString();
        String newPassword = new Sha256Hash(password, salt, MyType.ENCIPHER_CONST).toBase64();
        Map<String, String> map = new HashMap<>();
        map.put("password", newPassword);
        map.put("salt", salt);
        return map;
    }
}

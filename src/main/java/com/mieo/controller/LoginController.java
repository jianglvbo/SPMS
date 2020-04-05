package com.mieo.controller;

import com.mieo.model.Member;
import com.mieo.service.MemberService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("login")
public class LoginController {
    @Autowired
    MemberService memberService;
    @Autowired
    Member member;

    @RequestMapping("test")
    public String test(String username, String password) {
        username = "15960717193";
        password = "123";
        //1.获取Shiro的subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //3.执行登录校验
        try {
            //使用realm中的认证逻辑进行认证
            subject.login(token);
            //认证成功后的操作
            //页面信息初始化
            webInit(username);
        } catch (UnknownAccountException e) {
            //用户名不存在
            log.debug("用户名不存在");
        } catch (IncorrectCredentialsException e) {
            //密码错误
            log.debug("用户名存在但密码错误");
        }
        return "index";
    }


    @RequestMapping("verifyAccount")
    @ResponseBody
    public Map<String, String> verifyAccount(@RequestBody Map<String, String> map1) {
        String username = MapUtils.getString(map1, "username");
        String password = MapUtils.getString(map1, "password");
        Map<String, String> map = new HashMap<>();
        map.put("temp", "1");
        //1.获取Shiro的subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //3.执行登录校验
        try {
            //使用realm中的认证逻辑进行认证
            subject.login(token);
            //认证成功后的操作
            map.put("msg", "登录成功，正在跳转页面");
            //页面信息初始化
            webInit(username);
        } catch (UnknownAccountException e) {
            //用户名不存在
            log.debug("用户名不存在");
            map.put("temp", "0");
            map.put("msg", "账号或密码错误，请重新输入");
        } catch (IncorrectCredentialsException e) {
            //密码错误
            log.debug("用户名存在但密码错误");
            map.put("temp", "0");
            map.put("msg", "账号或密码错误，请重新输入");
        }
        return map;
    }

    //发送信息到钉钉
    @RequestMapping("/dingTalk")
    public void dingTalk(HttpServletRequest request, HttpServletResponse response) throws IOException, ApiException {

    }

    /**
     * 页面的准备
     */
    private void webInit(String username) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        session.setTimeout(10000000);
        System.out.println(session.getClass());
        session.setAttribute("user", memberService.queryMemberInfoByAccount(username));
    }

    @RequestMapping("toLogin")
    public String toLogin() {
        log.debug("跳转到登录页面");
        return "login";
    }


}

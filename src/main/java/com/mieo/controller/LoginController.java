package com.mieo.controller;

import com.mieo.common.util.dingTalk.DingTalkText;
import com.mieo.mapper.LoginMapper;
import com.mieo.model.User;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/login")
public class LoginController {

    @Autowired
    LoginMapper loginMapper;

    @RequestMapping("/verifyAccount")
    public ModelAndView verifyAccount(String username, String password, String verifyCode, HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("message","用户名或密码不正确");
        modelAndView.setViewName("login");
        if("0".equals(verifyCode)){
            modelAndView.addObject("message","请完成验证后登录");
            modelAndView.setViewName("login");
            return modelAndView;
        }
        List<User> list=loginMapper.queryUserInfo();
        for (User user : list) {
            if(user.getUsername().equals(username)&&user.getPassword().equals(password)){
                request.getSession().setAttribute("user",user);
                modelAndView.setViewName("index");
                return modelAndView;
            }
        }
        return modelAndView;
    }
    @RequestMapping("/test")
    public String  test(){

        return "test";
    }

    @RequestMapping("/verifyUsername")
    public @ResponseBody  Map<String,String> verifyUsername(@RequestBody Map<String,String> map){
        Map<String,String> result=new HashMap<>();
        List<User> list=loginMapper.queryUserInfo();
        String username= map.get("username");
        for (User user : list) {
            if(user.getUsername().equals(username)){
                result.put("message", "用户名正确");
                return result;
            }
        }
        result.put("message", "用户不存在");
        return result;
    }

    @RequestMapping("/dingTalk")
    public void dingTalk(HttpServletRequest request, HttpServletResponse response) throws IOException, ApiException {
        String isAtAll="true";
        List<String > mobiles=new ArrayList<>();
        mobiles.add("15960717193");
        String content="好吧好22124吧";
        DingTalkText dingTalkText=new DingTalkText(isAtAll, mobiles, content);
        dingTalkText.dingTalkExecute();
    }




}

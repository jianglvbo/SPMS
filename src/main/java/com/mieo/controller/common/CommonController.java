package com.mieo.controller.common;

import com.mieo.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("common")
public class CommonController {
    @Autowired
    MemberService memberService;
    @RequestMapping("toIndex")
    public String toIndex() {
        log.debug("跳转到首页");
        return "index";
    }

    @RequestMapping("toNoPermission")
    public String toPermission() {
        log.debug("跳转到未授权页面");
        return "no_permission";
    }

    @RequestMapping("toError")
    public String toError() {
        log.debug("跳转到错误页面");
        return "error";
    }

    @RequestMapping("toStatistics")
    public String toStatistics() {
        log.debug("跳转到统计页面");
        return "statistics";
    }






}

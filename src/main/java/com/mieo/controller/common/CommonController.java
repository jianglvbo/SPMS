package com.mieo.controller.common;

import com.mieo.service.MemberService;
import com.mieo.service.ProjectService;
import com.mieo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("common")
public class CommonController {
    @Autowired
    MemberService memberService;
    @Autowired
    ProjectService projectService;
    @Autowired
    TaskService taskService;

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
        return "archive";
    }



    @RequestMapping("toAlterPassword")
    public String toAlterPassword() {
        log.debug("跳转到修改密码页面");
        return "alter_password";
    }

    @RequestMapping("queryIndexCount")
    @ResponseBody
    public Map<String,Integer> queryIndexCount(int memberId,int role) {
        Map<String,Integer> map=new HashMap<>();
        int i = projectService.queryProjectCountByMemberIdAndRole(memberId,role);
        int i1 = taskService.queryTaskCountByMemberId(memberId,role);
        map.put("projectCount",i);
        map.put("taskCount",i1);
        return map;
    }

    @RequestMapping("toArchive")
    public ModelAndView toArchive() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("archive");
        return modelAndView;
    }



}

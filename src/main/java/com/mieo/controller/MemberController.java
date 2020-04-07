package com.mieo.controller;

import com.mieo.common.util.Encipher;
import com.mieo.model.Member;
import com.mieo.service.MemberService;
import jdk.nashorn.internal.ir.ReturnNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@Slf4j
@RequestMapping("member")
public class MemberController {
    @Autowired
    MemberService memberService;
    @Autowired
    Encipher encipher;

    /**
     * 添加成员
     *
     * @param member
     * @return
     */
    @RequestMapping("addMember")
    @ResponseBody
    public Map<String, String> addMember(@RequestBody Member member) {
        Map<String, String> map = encipher.encypt(member.getMemberPassword());
        member.setMemberPassword(MapUtils.getString(map, "password"));
        member.setMemberSalt(MapUtils.getString(map, "salt"));
        memberService.addMember(member);
        Map<String, String> msg = new HashMap<>();
        msg.put("msg", "添加成功");
        return map;
    }

    /**
     * 通过id删除成员信息
     *
     * @param map
     * @return
     */
    @RequestMapping("deleteMemberById")
    @ResponseBody
    public String deleteMemberById(@RequestBody Map<String, Object> map) {
        int id = MapUtils.getInteger(map, "id");
        int statu = memberService.deleteMemberById(id);
        if (statu != 1) {
            log.error("删除id为 " + id + " 的成员失败");
        } else {
            log.debug("删除id为 " + id + " 的成员成功");
        }
        return Integer.toString(statu);
    }

    /**
     * 修改成员的密码
     * @return
     */
    @RequestMapping("updateMemberPasswordByPhone")
    @ResponseBody
    public Map<String,String> updateMemberPasswordByPhone(String password,String phone) {
        Member member=new Member();
        Map<String, String> map1 = encipher.encypt(password);
        member.setMemberPassword(MapUtils.getString(map1, "password"));
        member.setMemberSalt(MapUtils.getString(map1, "salt"));;
        member.setMemberPhone(phone);
        memberService.updateMemberPasswordByPhone(member);
        Map<String,String> map2=new HashMap<>();
        return map2;
    }


    /**
     * 批量删除成员信息
     *
     * @param ids
     * @return
     */
    @RequestMapping("removeMemberByIds")
    @ResponseBody
    public Map<String, String> removeMemberByIds(@RequestBody List<Integer> ids) {
        memberService.deleteMemberByIds(ids);
        Map<String, String> msg = new HashMap<>();
        msg.put("msg", "删除成功");
        return msg;
    }

    /**
     * 跳转到修改成员信息页面
     *
     * @param memberId 编辑的成员
     * @return
     */
    @RequestMapping("toEditMember")
    public ModelAndView toEditMember(int memberId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", memberService.queryMemberById(memberId));
        modelAndView.setViewName("member_edit");
        return modelAndView;
    }

    /**
     * 更新成员信息
     *
     * @param member
     * @return
     */
    @RequestMapping("updateMember")
    @ResponseBody
    public Map<String, String> updateMemberById(@RequestBody Member member) {
        memberService.updateMemberById(member);
        Map<String, String> msg = new HashMap<>();
        msg.put("msg", "成员修改成功");
        return msg;
    }

    /**
     * 通过id查询成员信息
     *
     * @return 被查询的成员信息
     */
    @RequestMapping("queryMemberById")
    @ResponseBody
    public Member queryMemberById(@RequestBody Map<String, String> map) {
        return memberService.queryMemberById(Integer.parseInt(MapUtils.getString(map, "id")));
    }

    /**
     * 查询所有的成员信息,前端bootstrap-table接口
     *
     * @return 包含所有成员信息的链表
     */
    @RequestMapping("queryMemberAll")
    @ResponseBody
    public List<Member> queryMemberAll() {
        return memberService.queryAllMemberInfo();
    }

    /**
     * 查询同一团队下的所有成员
     * @param teamId
     * @return
     */
    @RequestMapping("queryMemberByTeamId")
    @ResponseBody
    public List<Member> queryMemberByTeamId(int teamId){
        return memberService.queryMemberByTeamId(teamId);
    }

    /**
     * 跳到成员详情任务页面
     *
     * @param memberId
     * @return
     */
    @RequestMapping("toMemberDetailProject")
    public ModelAndView toMemberDetailProject(int memberId) {
        log.debug("跳转到用户详情项目页面");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", memberService.queryMemberById(memberId));
        modelAndView.setViewName("member_detail_project");
        return modelAndView;
    }

    /**
     * 跳转到成员详情动态页面
     *
     * @param memberId
     * @return
     */
    @RequestMapping("toMemberDetailDynamicState")
    public ModelAndView toMemberDetailDynamicState(int memberId) {
        log.debug("跳转到用户详情页面");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", memberService.queryMemberById(memberId));
        modelAndView.setViewName("member_detail_dynamic_state");
        return modelAndView;
    }

    /**
     * 跳转到成员详情任务页面
     *
     * @param memberId
     * @return
     */
    @RequestMapping("toMemberDetailTask")
    public ModelAndView toMemberDetailTask(int memberId) {
        log.debug("跳转到用户详情页面");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("member", memberService.queryMemberById(memberId));
        modelAndView.setViewName("member_detail_task");
        return modelAndView;
    }

    /**
     * 跳转到成员添加页面
     *
     * @return
     */
    @RequestMapping("toMemberAdd")
    public String toAddMember() {
        log.debug("跳转到新增成员页面");
        return "member_add";
    }

    /**
     * 跳转到成员页面
     *
     * @return
     */
    @RequestMapping("toMember")
    public String toMember() {
        log.debug("跳转到成员页面");
        return "member";
    }
}

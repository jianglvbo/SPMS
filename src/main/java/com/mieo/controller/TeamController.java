package com.mieo.controller;

import com.mieo.model.DynamicState;
import com.mieo.model.Team;
import com.mieo.service.DynamicStateService;
import com.mieo.service.TeamService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultMap;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("team")
@Slf4j
@Controller
public class TeamController {
    @Autowired
    TeamService teamService;
    @Autowired
    DynamicStateService dynamicStateService;
    /**
     * 跳转到团队页面
     * @return
     */
    @RequestMapping("toTeam")
    public String toTeam(){
//        //初始化团队信息
//        List<Integer> integers = teamService.queryTeamNull();
//        for (Integer integer : integers) {
//            teamService.deleteTeamByTeamId(integer);
//        }
        return "team";
    }

    /**
     * 跳转到团队详情页面
     * @return
     */
    @RequestMapping("toTeamDetail")
    public ModelAndView toTeamDetail(int teamId){
        Team team = teamService.queryTeamByTeamId(teamId);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("team", team);
        modelAndView.setViewName("team_detail");
        return modelAndView;
    }

    /**
     * 通过团队id删除团队信息
     * @param teamId
     * @return
     */
    @RequestMapping("deleteTeamByTeamId")
    @ResponseBody
    public Map<String,String> deleteTeamByTeamId(int teamId){
        teamService.deleteTeamByTeamId(teamId);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg", "删除成功");
        return msg;
    }

    /**
     * 通过团队id删除团队信息
     * @return
     */
    @RequestMapping("deleteTeamByTeamIds")
    @ResponseBody
    public Map<String,String> deleteTeamByTeamIds(@RequestBody List<Integer> teamIds){
        for (Integer teamId : teamIds) {
            teamService.deleteTeamByTeamId(teamId);
        }
        Map<String,String> msg=new HashMap<>();
        msg.put("msg", "删除成功");
        return msg;
    }

    @RequestMapping("updateTeam")
    @ResponseBody
    public Map<String,String> updateTeamByTeamId(@RequestBody Team team){
        teamService.updateTeamByTeamId(team);
        DynamicState dynamicState=new DynamicState("修改了","团队信息","团队",team.getTeamId());
        dynamicStateService.addDynamicState(dynamicState);
        Map<String ,String> msg=new HashMap<>();
        msg.put("msg", "修改成功");
        return msg;
    }

    /**
     * 查询所有团队的所有信息
     * @return
     */
    @RequestMapping("queryTeamAll")
    @ResponseBody
    public List<Team> queryTeamAll() {
        return teamService.queryTeamAll();
    }

    /**
     * 查询所有团队的信息
     * @return
     */
    @RequestMapping("queryAllTeam")
    @ResponseBody
    public List<Team> queryAllTeam() {
        return teamService.queryAllTeam();
    }

    /**
     * 添加团队信息
     * @param team
     * @return
     */
    @RequestMapping("addTeam")
    @ResponseBody
    public Map<String,String> addTeam(@RequestBody Team team) {
        teamService.addTeam(team);
        Map<String,String> msg=new HashMap<>();
        msg.put("msg", "添加成功");
        return msg;
    }

    @RequestMapping("queryTeamByTeamId")
    public ModelAndView queryTeamByTeamId(int teamId){
        Team team = teamService.queryTeamByTeamId(teamId);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("team", team);
        modelAndView.setViewName("team_detail");
        return  modelAndView;
    }
}

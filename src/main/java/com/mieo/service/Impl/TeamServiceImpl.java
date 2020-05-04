package com.mieo.service.Impl;

import com.mieo.common.util.DingTalkUtil;
import com.mieo.mapper.TeamMapper;
import com.mieo.model.DynamicState;
import com.mieo.model.Team;
import com.mieo.service.DynamicStateService;
import com.mieo.service.MemberService;
import com.mieo.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    DingTalkUtil dingTalkUtil;
    @Autowired
    DynamicStateService dynamicStateService;
    @Autowired
    MemberService memberService;

    /**
     * 添加团队信息
     *
     * @param team 团队信息
     */
    @Override
    public void addTeam(Team team) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        team.setTeamCreateTime(df.format(new Date()));
        teamMapper.addTeam(team);
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("添加团队：  "+team.getTeamName(), team.getTeamDescription(), "http://localhost/team/toTeamDetail?teamId="+team.getTeamId());
        //动态
        DynamicState dynamicState = new DynamicState("添加了团队", team.getTeamName(), "团队", team.getTeamId());
        dynamicStateService.addDynamicState(dynamicState);
    }

    /**
     * 通过团队id删除团队信息
     *
     * @param teamId 团队id
     */
    @Override
    public void deleteTeamByTeamId(int teamId) {
        Team team = teamMapper.queryTeamByTeamId(teamId);
        //钉钉
        dingTalkUtil.dingTalkTextAtOne("删除团队:   " + team.getTeamName());
        //删除动态
        DynamicState dynamicState = new DynamicState("删除了项目", team.getTeamName(), "普通类型", 0);
        DynamicState dynamicState1=new DynamicState("","","团队",teamId);
        dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
        dynamicStateService.addDynamicState(dynamicState);
        teamMapper.deleteTeamByTeamId(teamId);
    }

    /**
     * 更新团队信息
     *
     * @param team
     */
    @Override
    public void updateTeamByTeamId(Team team) {
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("修改团队:  "+team.getTeamName(), team.getTeamDescription(), "http://localhost/team/toTeamDetail?teamId="+team.getTeamId());
        //动态
        DynamicState dynamicState = new DynamicState("修改了项目", team.getTeamName(), "项目", team.getTeamId());
        dynamicStateService.addDynamicState(dynamicState);
        teamMapper.updateTeamByTeamId(team);
    }



    /**
     * 查询所有的团队信息
     *
     * @return 所有的团队信息
     */
    @Override
    public List<Team> queryAllTeam() {
        return teamMapper.queryAllTeam();
    }

    /**
     * 通过团队id查询团队信息
     *
     * @param id 团队id
     * @return 团队信息
     */
    @Override
    public Team queryTeamByTeamId(int id) {
        return teamMapper.queryTeamByTeamId(id);
    }

    /**
     * 通过团队id查询团队名称
     *
     * @param id
     * @return
     */
    @Override
    public String queryTeamNameByTeamId(Integer id) {
        return teamMapper.queryTeamNameByTeamId(id);
    }

    /**
     * 查询所有团队信息及其成员
     * @return 团队信息及其成员
     */
    @Override
    public List<Team> queryTeamAll() {
        return teamMapper.queryTeamAll();
    }

    /**
     * 查询为空的团队id
     *
     * @return 删除为空的团队
     */
    @Override
    public List<Integer> queryTeamNull() {
        return teamMapper.queryTeamNull();
    }
}

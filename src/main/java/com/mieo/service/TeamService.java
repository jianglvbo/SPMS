package com.mieo.service;

import com.mieo.model.Member;
import com.mieo.model.Team;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TeamService {

    /**
     * 添加团队信息
     *
     * @param team 团队信息
     */
    void addTeam(Team team);

    /**
     * 通过团队id删除团队信息
     *
     * @param teamId 团队id
     */
    void deleteTeamByTeamId(int teamId);


    /**
     * 更新团队信息
     * @param team
     */
    void updateTeamByTeamId(Team team);



    /**
     * 查询所有的团队信息
     *
     * @return 所有的团队信息
     */
    List<Team> queryAllTeam();

    /**
     * 通过团队id查询团队信息
     *
     * @param id 团队id
     * @return 团队信息
     */
    Team queryTeamByTeamId(int id);

    /**
     * 通过团队id查询团队名称
     * @param id
     * @return
     */
    String queryTeamNameByTeamId(Integer id);

    /**
     * 查询所有团队信息及其成员
     *
     * @return 团队信息及其成员
     */
    public List<Team> queryTeamAll();


    /**
     * 查询为空的团队id
     *
     * @return 删除为空的团队
     */
    List<Integer> queryTeamNull();
}

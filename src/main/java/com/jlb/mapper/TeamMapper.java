package com.jlb.mapper;

import com.jlb.model.Team;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMapper {

    /**
     * 添加团队信息
     *
     * @param team 团队信息
     */
    @Insert(" insert into team (team_name, team_description, team_create_time)" +
            " values (#{teamName},#{teamDescription},#{teamCreateTime})")
    @Options(useGeneratedKeys = true,keyProperty = "teamId",keyColumn = "team_id")
    void addTeam(Team team);

    /**
     * 通过团队id删除团队信息
     *
     * @param teamId 团队id
     */
    @Delete("delete from team where team_id=#{teamId}")
    void deleteTeamByTeamId(int teamId);

    /**
     * 查询所有的团队信息
     *
     * @return 团队信息
     */
    @Select("select * from team")
    List<Team> queryAllTeam();

    /**
     * 通过团队id查询团队信息
     *
     * @param id 团队id
     * @return 团队信息
     */
    @Select("select * from team where team_id=#{id}")
    Team queryTeamByTeamId(int id);


    /**
     * 查询所有的成员信息,前端bootstrap-table接口
     *
     * @return 包含所有成员信息的链表
     */
    @Select("select * from team")
    @Results(id = "team_members", value = {
            @Result(property = "teamId", column = "team_id"),
            @Result(property = "teamMembers", column = "team_id", many = @Many(select = "com.jlb.mapper.MemberMapper.queryMemberByTeamId", fetchType = FetchType.EAGER))
    })
    List<Team> queryTeamAll();

    /**
     * 通过团队id查询团队名称
     * @param id
     * @return
     */
    @Select("select team_name from team where team_id=#{id}")
    String queryTeamNameByTeamId(Integer id);

    /**
     * 查询为空的团队id
     *
     * @return 删除为空的团队
     */
    @Select("SELECT team_id FROM team LEFT JOIN  member on team_id=member_team_id  where ISNULL(member_name)")
    List<Integer> queryTeamNull();

    /**
     * 更新团队信息
     * @param team
     */
    @Update("update team set team_name=#{teamName},team_description=#{teamDescription},team_create_time=#{teamCreateTime} where team_id=#{teamId}")
    void updateTeamByTeamId(Team team);
}

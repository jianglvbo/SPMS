package com.mieo.mapper;

import com.mieo.model.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectMapper {

    /**
     * 添加项目
     *
     * @param project
     */
    @Insert("insert into project (project_name,project_start_time, project_end_time, " +
            "                     project_schedule, project_principal_id,project_principal_name,project_principal_team_id,project_principal_team_name,project_content) " +
            "values (#{projectName},#{projectStartTime},#{projectEndTime},#{projectSchedule},#{projectPrincipalId},#{projectPrincipalName},#{projectPrincipalTeamId},#{projectPrincipalTeamName},#{projectContent})")
    @Options(useGeneratedKeys = true, keyProperty = "projectId", keyColumn = "project_id")
    void addProject(Project project);


    /**
     * 删除项目
     *
     * @param projectId
     */
    @Delete("delete from project where project_id=#{projectId}")
    void deleteProjectByProjectId(int projectId);

    /**
     * 通过项目id修改对应项目信息
     *
     * @param project
     */
    @Update("UPDATE project " +
            "SET project_name =#{projectName}," +
            "project_start_time = #{projectStartTime}," +
            "project_end_time = #{projectEndTime}," +
            "project_schedule = #{projectSchedule}," +
            "project_principal_id = #{projectPrincipalId}," +
            "project_principal_name = #{projectPrincipalName}," +
            "project_principal_team_id = #{projectPrincipalTeamId}," +
            "project_principal_team_name = #{projectPrincipalTeamName}," +
            "project_content=#{projectContent}  where project_id=#{projectId}")
    void updateProjectByProjectId(Project project);

    /**
     * 通过id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    @Select("select * from project where project_id=#{id}")
    @ResultMap("project_all_info")
    Project queryProjectByProjectId(Integer projectId);

    /**
     * 通过项目id查询项目名称
     *
     * @param id
     * @return
     */
    @Select("select project_name from project where project_id=#{id}")
    String queryProjectNameByProjectId(Integer id);

    /**
     * 查询所有的项目信息
     *
     * @return 所有项目信息
     */
    @Select("SELECT * from project where project_isArchive='0' ")
    @Results(id = "project_all_info", value = {
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectPrincipalId", column = "project_principal_id"),
            @Result(property = "projectPrincipalTeamId", column = "project_principal_team_id"),
            @Result(property = "projectPrincipal", column = "project_principal_id", one = @One(select = "com.mieo.mapper.MemberMapper.queryMemberById")),
            @Result(property = "projectPrincipalTeam", column = "project_principal_team_id", one = @One(select = "com.mieo.mapper.TeamMapper.queryTeamByTeamId"))
    })
    List<Project> queryProjectAll();

    /**
     * 查询所有的未归档项目信息
     *
     * @return 所有项目信息
     */
    @Select("SELECT * from project where project_isArchive='0' ")
    @ResultMap("project_all_info")
    List<Project> queryProjectAllNotArchive();

    /**
     * 查询已归档的所有项目
     *
     * @return
     */
    @Select("select * from project where project_isArchive='1'")
    List<Project> queryProjectAllArchive();

    /**
     * 通过项目id查询已归档项目信息
     *
     * @param projectId
     * @return
     */
    @Select("select * from project where project_id=#{projectId}")
    Project queryProjectArchiveByProjectId(Integer projectId);

    /**
     * 查询所有项目数量
     *
     * @return
     */
    @Select("select count(*) from project where project_isArchive='0'")
    int queryProjectCountAll();

    /**
     * 通过用户id查询用户参与的项目
     *
     * @param id 用户id
     * @return 项目信息
     */
    @Select("SELECT" +
            " * " +
            "FROM" +
            " project " +
            "WHERE project_isArchive='0'" +
            "And project_principal_team_id = ( SELECT member_team_id FROM member WHERE member_id =  #{id} ) OR project_principal_id= #{id}")
    @ResultMap("project_all_info")
    List<Project> queryProjectByMemberId(Integer id);

    /**
     * 查询用户负责的项目数
     *
     * @param memberId
     * @return
     */
    @Select("SELECT" +
            " count(*) " +
            "FROM" +
            " project " +
            "WHERE project_isArchive='0' and " +
            " project_principal_team_id = ( SELECT member_team_id FROM member WHERE member_id =  #{id} ) OR project_principal_id= #{id}")
    int queryProjectCountByMemberId(int memberId);

    /**
     * 通过团队id查询团队负责的项目数
     * @param teamId
     * @return
     */
    @Select("select count(*) from project where project_principal_team_id=#{teamId} and project_isArchive='0'")
    int queryProjectCountByTeamId(int teamId);

    /**
     * 根据项目id归档对应项目
     *
     * @param projectId
     */
    @Update("UPDATE project SET project_isArchive='1' WHERE project_id=#{projectId}")
    void archiveProjectByProjectId(int projectId);
}

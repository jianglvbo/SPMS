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
    @Insert("insert into project (project_name, project_create_id, project_start_time, project_end_time, " +
            "                     project_schedule, project_principal_id, project_principal_team_id,project_content) " +
            "values (#{projectName},#{projectCreateId},#{projectStartTime},#{projectEndTime},#{projectSchedule},#{projectPrincipalId},#{projectPrincipalTeamId},#{projectContent})")
    @Options(useGeneratedKeys = true,keyProperty = "projectId",keyColumn = "project_id")
    void addProject(Project project);


    /**
     * 删除项目
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
            "project_principal_team_id = #{projectPrincipalTeamId}," +
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
     * 查询所有的项目信息
     *
     * @return 所有项目信息
     */
    @Select("SELECT * from project ")
    @Results(id = "project_all_info", value = {
            @Result(property = "projectId", column = "project_id"),
            @Result(property = "projectCreator", column = "project_create_id", one = @One(select = "com.mieo.mapper.MemberMapper.queryMemberById")),
            @Result(property = "projectPrincipal", column = "project_principal_id", one = @One(select = "com.mieo.mapper.MemberMapper.queryMemberById")),
            @Result(property = "projectPrincipalTeam", column = "project_principal_team_id", one = @One(select = "com.mieo.mapper.TeamMapper.queryTeamByTeamId"))
    })
    List<Project> queryProjectAll();

    /**
     * 通过用户id查询用户参与的项目
     *
     * @param id 用户id
     * @return 项目信息
     */
    @Select("SELECT * FROM project WHERE project_principal_id=#{id} OR project_create_id=#{id}")
    @ResultMap("project_all_info")
    List<Project> queryProjectByMemberId(Integer id);


}

package com.jlb.service;

import com.jlb.model.Project;

import java.util.List;
import java.util.Map;

public interface ProjectService {

    /**
     * 添加项目
     *
     * @param project
     */
    void addProject(Project project);

    /**
     * 删除项目
     *
     * @param projectId
     */
    void deleteProjectByProjectId(int projectId);

    /**
     * 批量删除项目信息
     *
     * @param projectIds
     */
    Map<String, String> removeProjectByProjectIds(List<Integer> projectIds);

    /**
     * 通过项目id修改对应项目信息
     *
     * @param project
     */
    void updateProjectByProjectId(Project project);

    /**
     * 查询所有的项目信息
     *
     * @return 所有项目信息
     */
    List<Project> queryProjectAll();

    /**
     * 查询已归档的所有项目
     * @return
     */
    List<Project> queryProjectAllArchive();

    /**
     * 通过id查询项目详情
     * @param projectId
     * @return
     */
    Project queryProjectArchiveByProjectId(int projectId);

    /**
     * 通过用户id查询用户参与的项目
     *
     * @return 项目信息
     */
    List<Project> queryProjectByMemberIdAndRole(int memberId, int role);

    /**
     * 通过项目id查询项目名称
     * @param projectId
     * @return
     */
    String queryProjectNameByProjectId(int projectId);

    /**
     * 通过用户id查询用户参加的项目
     * @param memberId
     * @return
     */
    List<Project> queryProjectByMemberId(int memberId);

    /**
     * 查询用户负责的项目数
     *
     * @param memberId
     * @return
     */
    int queryProjectCountByMemberIdAndRole(int memberId, int role);

    /**
     * 通过团队id查询团队负责的项目数
     * @param teamId
     * @return
     */
    int queryProjectCountByTeamId(int teamId);


    /**
     * 通过id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    Project queryProjectByProjectId(Integer projectId);

    /**
     * 通过成员id和成员角色归档项目
     */
    Map<String,String> archiveProjectByProjectIds(List<Integer> projects);
}

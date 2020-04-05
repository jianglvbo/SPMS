package com.mieo.service;

import com.mieo.model.Project;

import java.util.List;

public interface ProjectService {

    /**
     * 添加项目
     * @param project
     */
    void addProject(Project project);

    /**
     * 删除项目
     * @param projectId
     */
    void deleteProjectByProjectId(int projectId);

    /**
     * 批量删除项目信息
     * @param projectIds
     */
    void removeProjectByProjectIds(List<Integer> projectIds);

    /**
     * 通过项目id修改对应项目信息
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
     * 通过用户id查询用户参与的项目
     *
     * @param id 用户id
     * @return 项目信息
     */
    List<Project> queryProjectByMemberId(Integer id);

    /**
     * 通过id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    Project queryProjectByProjectId(Integer projectId);

}

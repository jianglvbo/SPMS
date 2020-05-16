package com.jlb.controller;

import com.jlb.model.Project;
import com.jlb.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @RequestMapping("addProject")
    @ResponseBody
    public Map<String, String> addProject(@RequestBody Project project) {
        projectService.addProject(project);
        Map<String, String> msg = new HashMap<>();
        msg.put("msg", "添加成功");
        return msg;
    }

    /**
     * 批量删除项目信息
     * @param ids
     * @return
     */
    @RequestMapping("removeProjectByProjectIds")
    @ResponseBody
    public Map<String, String> removeProjectByProjectIds(@RequestBody List<Integer> ids) {
        return projectService.removeProjectByProjectIds(ids);
    }

    /**
     * 通过项目id修改项目信息
     *
     * @param project
     * @return
     */
    @RequestMapping("updateProjectByProjectId")
    @ResponseBody
    public Map<String, String> updateProjectByProjectId(@RequestBody Project project) {
        projectService.updateProjectByProjectId(project);
        Map<String, String> msg = new HashMap<>();
        msg.put("msg", "修改成功");
        return msg;
    }


    /**
     * 查询所有的项目信息
     *
     * @return 所有项目信息
     */
    @RequestMapping("queryProjectAll")
    @ResponseBody
    public List<Project> queryProjectAll() {
        return projectService.queryProjectAll();
    }


    /**
     * 查询所有的已归档项目信息
     *
     * @return 所有项目信息
     */
    @RequestMapping("queryProjectArchiveAll")
    @ResponseBody
    public List<Project> queryProjectArchiveAll() {
        return projectService.queryProjectAllArchive();
    }


    /**
     * 通过用户id和角色查询用户参与的项目
     *
     * @return 项目信息
     */
    @RequestMapping("queryProjectByMemberIdAndRole")
    @ResponseBody
    public List<Project> queryProjectByMemberIdAndRole(int memberId,int role) {
        return projectService.queryProjectByMemberIdAndRole(memberId,role);
    }

    /**
     * 通过用户id查询用户参与的项目
     *
     * @return 项目信息
     */
    @RequestMapping("queryProjectByMemberId")
    @ResponseBody
    public List<Project> queryProjectByMemberId(int memberId) {
        return projectService.queryProjectByMemberId(memberId);
    }



    @RequestMapping("toProject")
    public String toProject() {
        log.debug("跳转到项目页面");
        return "project";
    }

    @RequestMapping("toProjectAdd")
    public String toProjectAdd() {
        log.debug("跳转到项目添加页面");
        return "project_add";
    }


    @RequestMapping("toProjectDetail")
    public ModelAndView toProjectDetail(int projectId) {
        Project project = projectService.queryProjectByProjectId(projectId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("project", project);
        modelAndView.setViewName("project_detail");
        log.debug("跳转到项目详情页面");
        return modelAndView;
    }

    @RequestMapping("toArchiveProjectDetail")
    public ModelAndView toArchiveProjectDetail(int projectId){
        Project project = projectService.queryProjectArchiveByProjectId(projectId);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("archive_project_detail");
        modelAndView.addObject("project", project);
        return modelAndView;
    }

    @RequestMapping("queryArchiveProjectDetail")
    @ResponseBody
    public Project queryArchiveProject(int projectId){
        return projectService.queryProjectArchiveByProjectId(projectId);
    }

    @RequestMapping("archiveProjectByProjectIds")
    @ResponseBody
    public Map<String,String> archiveProjectByProjectIds(@RequestBody List<Integer> ids){
        return projectService.archiveProjectByProjectIds(ids);
    }
}

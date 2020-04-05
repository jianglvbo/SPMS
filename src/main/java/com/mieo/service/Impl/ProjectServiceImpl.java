package com.mieo.service.Impl;

import com.mieo.common.util.DingTalkUtil;
import com.mieo.mapper.ProjectMapper;
import com.mieo.model.Comment;
import com.mieo.model.DynamicState;
import com.mieo.model.Member;
import com.mieo.model.Project;
import com.mieo.service.CommentService;
import com.mieo.service.DynamicStateService;
import com.mieo.service.MemberService;
import com.mieo.service.ProjectService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectMapper projectMapper;
    @Autowired
    DynamicStateService dynamicStateService;
    @Autowired
    DingTalkUtil dingTalkUtil;
    @Autowired
    MemberService memberService;
    @Autowired
    CommentService commentService;

    /**
     * 添加项目
     *
     * @param project
     */
    @Override
    public void addProject(Project project) {
        projectMapper.addProject(project);
        dingTalkUtil.dingTalkLinkAtOne("创建项目:   " + project.getProjectName(), "  " + project.getProjectContent(), "http://localhost/project/toProjectDetail?projectId=" + project.getProjectId());
        DynamicState dynamicState = new DynamicState("添加了项目", project.getProjectName(), "项目", project.getProjectId());
        dynamicStateService.addDynamicState(dynamicState);
    }

    /**
     * 删除项目
     *
     * @param projectId
     */
    @Override
    public void deleteProjectByProjectId(int projectId) {
        Project project = projectMapper.queryProjectByProjectId(projectId);
        //钉钉
        dingTalkUtil.dingTalkTextAtOne("删除项目:   " + project.getProjectName());
        //删除动态
        DynamicState dynamicState = new DynamicState("删除了项目", project.getProjectName(), "普通类型", 0);
        DynamicState dynamicState1=new DynamicState("","","项目",projectId);
        dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
        dynamicStateService.addDynamicState(dynamicState);
        //删除评论
        Comment comment=new Comment("","项目",project.getProjectId());
        commentService.deleteCommentByComment(comment);
        projectMapper.deleteProjectByProjectId(projectId);
    }

    /**
     * 批量删除项目信息
     *
     * @param projectIds
     */
    @Override
    public void removeProjectByProjectIds(List<Integer> projectIds) {
        for (Integer projectId : projectIds) {
            Project project = projectMapper.queryProjectByProjectId(projectId);
            //钉钉
            dingTalkUtil.dingTalkTextAtOne("删除项目:   " + project.getProjectName());
            //删除动态
            DynamicState dynamicState = new DynamicState("删除了项目", project.getProjectName(), "普通类型", 0);
            DynamicState dynamicState1=new DynamicState("","","项目",projectId);
            dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
            dynamicStateService.addDynamicState(dynamicState);
            //删除评论
            Comment comment=new Comment("","项目",project.getProjectId());
            commentService.deleteCommentByComment(comment);
            projectMapper.deleteProjectByProjectId(projectId);
        }
    }

    /**
     * 通过项目id修改对应项目信息
     *
     * @param project
     */
    @Override
    public void updateProjectByProjectId(Project project) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Member member = (Member) session.getAttribute("user");
        projectMapper.updateProjectByProjectId(project);
        dingTalkUtil.dingTalkLinkAtOne("修改项目: " + project.getProjectName(), project.getProjectContent(), "http://localhost/project/toProjectDetail?projectId=" + project.getProjectId());
        DynamicState dynamicState = new DynamicState("修改了项目", project.getProjectName(), "项目", project.getProjectId());
        dynamicStateService.addDynamicState(dynamicState);
    }

    /**
     * 查询所有的项目信息
     *
     * @return 所有项目信息
     */
    @Override
    public List<Project> queryProjectAll() {
        List<Project> projects = projectMapper.queryProjectAll();
        return projects;
    }

    /**
     * 通过用户id查询用户参与的项目
     *
     * @param id 用户id
     * @return 项目信息
     */
    @Override
    public List<Project> queryProjectByMemberId(Integer id) {
        return projectMapper.queryProjectByMemberId(id);
    }

    /**
     * 通过id查询项目信息
     *
     * @param projectId 项目id
     * @return 项目信息
     */
    @Override
    public Project queryProjectByProjectId(Integer projectId) {
        return projectMapper.queryProjectByProjectId(projectId);
    }
}

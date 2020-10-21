package com.jlb.service.Impl;

import com.jlb.common.util.DingTalkUtil;
import com.jlb.mapper.ProjectMapper;
import com.jlb.model.Comment;
import com.jlb.model.DynamicState;
import com.jlb.model.Member;
import com.jlb.model.Project;
import com.jlb.service.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    TaskService taskService;
    @Autowired
    TeamService teamService;

    /**
     * 添加项目
     *
     * @param project
     */
    @Override
    public void addProject(Project project) {
        String memberName = memberService.queryMemberNameByMemberId(project.getProjectPrincipalId());
        String teamName = teamService.queryTeamNameByTeamId(project.getProjectPrincipalTeamId());
        project.setProjectPrincipalName(memberName);
        project.setProjectPrincipalTeamName(teamName);
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
        DynamicState dynamicState1 = new DynamicState("", "", "项目", projectId);
        dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
        dynamicStateService.addDynamicState(dynamicState);
        //删除评论
        Comment comment = new Comment("", "项目", project.getProjectId());
        commentService.deleteCommentByComment(comment);
        projectMapper.deleteProjectByProjectId(projectId);
    }

    /**
     * 批量删除项目信息
     *
     * @param projectIds
     */
    @Override
    public Map<String, String> removeProjectByProjectIds(List<Integer> projectIds) {
        Map<String, String> msg = new HashMap<>();
        msg.put("temp", "success");
        msg.put("msg", "删除成功");
        for (Integer projectId : projectIds) {
            if (taskService.queryTaskCountByProjectId(projectId) > 0) {
                msg.put("temp", "danger");
                msg.put("msg", "该项目下的任务不为空,无法删除!!!");
                return msg;
            }
        }
        for (Integer projectId : projectIds) {
            Project project = projectMapper.queryProjectByProjectId(projectId);
            //钉钉
            dingTalkUtil.dingTalkTextAtOne("删除项目:   " + project.getProjectName());
            //删除动态
            DynamicState dynamicState = new DynamicState("删除了项目", project.getProjectName(), "普通类型", 0);
            DynamicState dynamicState1 = new DynamicState("", "", "项目", projectId);
            dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
            dynamicStateService.addDynamicState(dynamicState);
            //删除评论
            Comment comment = new Comment("", "项目", project.getProjectId());
            commentService.deleteCommentByComment(comment);
            projectMapper.deleteProjectByProjectId(projectId);
        }
        return msg;
    }

    /**
     * 通过用户id查询用户参与的项目
     *
     * @return 项目信息
     */
    @Override
    public List<Project> queryProjectByMemberIdAndRole(int memberId, int role) {
        if (role == 1) {
            return projectMapper.queryProjectAllNotArchive();
        } else {
            return projectMapper.queryProjectByMemberId(memberId);
        }
    }

    /**
     * 通过用户id和角色查询用户相关的归档信息
     * @param memberId
     * @param role
     * @return
     */
    @Override
    public List<Project> queryProjectArchiveByMemberIdAndRole(int memberId, int role) {
        if (role == 1) {
            return projectMapper.queryProjectAllArchive();
        } else {
            return projectMapper.queryProjectAllArchiveByMemberId(memberId);
        }
    }

    /**
     * 通过项目id查询项目名称
     *
     * @param projectId
     * @return
     */
    @Override
    public String queryProjectNameByProjectId(int projectId) {
        return projectMapper.queryProjectNameByProjectId(projectId);
    }

    /**
     * 通过用户id查询用户参加的项目
     *
     * @param memberId
     * @return
     */
    @Override
    public List<Project> queryProjectByMemberId(int memberId) {
        return projectMapper.queryProjectByMemberId(memberId);
    }

    /**
     * 查询用户负责的项目数
     *
     * @param memberId
     * @return
     */
    @Override
    public int queryProjectCountByMemberIdAndRole(int memberId, int role) {
        if (role == 1) {
            return projectMapper.queryProjectCountAll();
        } else {
            return projectMapper.queryProjectCountByMemberId(memberId);
        }
    }

    /**
     * 查询用户负责的项目数
     *
     * @param memberId
     * @param role
     * @return
     */
    @Override
    public int queryPrincipalProjectCountByMemberId(int memberId) {
        return projectMapper.queryPrincipalProjectCountByMemberId(memberId);
    }

    /**
     * 通过团队id查询团队负责的项目数
     *
     * @param teamId
     * @return
     */
    @Override
    public int queryProjectCountByTeamId(int teamId) {
        return projectMapper.queryProjectCountByTeamId(teamId);
    }

    /**
     * 通过项目id修改对应项目信息
     *
     * @param project
     */
    @Override
    public void updateProjectByProjectId(Project project) {
        String memberName = memberService.queryMemberNameByMemberId(project.getProjectPrincipalId());
        String teamName = teamService.queryTeamNameByTeamId(project.getProjectPrincipalTeamId());
        project.setProjectPrincipalName(memberName);
        project.setProjectPrincipalTeamName(teamName);
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
        return projectMapper.queryProjectAll();
    }

    /**
     * 查询已归档的所有项目
     *
     * @return
     */
    @Override
    public List<Project> queryProjectAllArchive() {
        return projectMapper.queryProjectAllArchive();
    }

    /**
     * 通过id查询项目详情
     *
     * @param projectId
     * @return
     */
    @Override
    public Project queryProjectArchiveByProjectId(int projectId) {
        return projectMapper.queryProjectArchiveByProjectId(projectId);
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

    /**
     * 通过成员id和成员角色归档项目
     */
    @Override
    public Map<String, String> archiveProjectByProjectIds(List<Integer> projectIds) {
        Map<String, String> msg = new HashMap<>();
        msg.put("temp", "success");
        msg.put("msg", "归档成功");
        //验证数据的合理性
        for (Integer projectId : projectIds) {
            Project project = projectMapper.queryProjectByProjectId(projectId);
            if (project.getProjectSchedule() != 100) {
                msg.put("msg", "项目进度未到100%");
                msg.put("temp", "danger");
                return msg;
            } else if(taskService.queryTaskNotArchiveCountByProjectId(projectId) != 0){
                msg.put("msg", "项目下还有未完成的任务");
                msg.put("temp", "danger");
                return msg;
            }
        }
        //日志保存
        for (Integer projectId : projectIds) {
            Project project = projectMapper.queryProjectByProjectId(projectId);
            //钉钉
            dingTalkUtil.dingTalkTextAtOne("归档项目:   " + project.getProjectName());
            //删除动态
            DynamicState dynamicState = new DynamicState("归档了项目", project.getProjectName(), "普通类型", 0);
            DynamicState dynamicState1 = new DynamicState("", "", "项目", projectId);
            dynamicStateService.deleteDynamicStateByDynamicState(dynamicState1);
            dynamicStateService.addDynamicState(dynamicState);
            //删除评论
            Comment comment = new Comment("", "项目", project.getProjectId());
            commentService.deleteCommentByComment(comment);
        }
        //项目归档
        for (Integer projectId : projectIds) {
            projectMapper.archiveProjectByProjectId(projectId);
        }
        return msg;
    }
}

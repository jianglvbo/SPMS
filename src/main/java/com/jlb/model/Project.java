package com.jlb.model;

import com.jlb.common.util.LocalTime;
import com.jlb.service.MemberService;
import com.jlb.service.TeamService;
import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
@Alias("project")
public class Project implements Serializable {
    @Autowired
    LocalTime localTime;
    @Autowired
    TeamService teamService;
    @Autowired
    MemberService memberService;

    private Integer projectId;
    private String projectName;
    private boolean projectPostpone;//项目是否延期
    private String projectStartTime;
    private String projectEndTime;
    private Integer projectPrincipalId;//项目负责人id
    private String projectPrincipalName;//项目负责人名字
    private Member projectPrincipal;//项目负责人
    private Integer projectPrincipalTeamId;//项目负责团队id
    private String projectPrincipalTeamName;//项目负责团队名称w
    private Team projectPrincipalTeam;//项目负责团队
    private Integer projectSchedule;//项目进度
    private String projectContent;//项目内容

    public Project() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Project;
    }

}

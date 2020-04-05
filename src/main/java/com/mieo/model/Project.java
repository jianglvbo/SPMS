package com.mieo.model;

import com.mieo.common.util.LocalTime;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Component
@Data
@Alias("project")
public class Project implements Serializable {
    @Autowired
    LocalTime localTime;

    private Integer projectId;
    private String projectName;
    private Integer projectCreateId;//创建者id
    private Member projectCreator;//创建者信息
    private boolean projectPostPhone;//项目是否延期
    private String projectStartTime;
    private String projectEndTime;
    private Integer projectPrincipalId;//项目负责人id
    private Member projectPrincipal;//项目负责人
    private Integer projectPrincipalTeamId;//项目负责团队id
    private Team projectPrincipalTeam;//项目负责团队
    private Integer projectSchedule;//项目进度
    private String projectContent;//项目内容

    public  void setProjectEndTime(String projectEndTime){
        this.projectEndTime=projectEndTime;
        this.projectPostPhone=localTime.isPostpone(projectEndTime);
    }
}

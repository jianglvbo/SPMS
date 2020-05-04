package com.mieo.model;

import com.mieo.common.util.LocalTime;
import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Alias("task")
@Component
public class Task implements Serializable {
    @Autowired
    LocalTime localTime;
    private Integer taskId;
    private String taskName;
    private String taskPriority;//任务优先级（0.默认优先级1.不重要,2.次要,3.主要,4.严重）
    private Integer taskCreateId;//创建者id
    private String taskCreateName;//创建名字
    private Member taskCreator;//创建者
    private Integer taskExecuteId;//执行人id
    private String taskExecuteName;//执行人名称
    private Member taskExecutor;//执行人
    private Integer taskTestId;//测试人id
    private String  taskTestName;//测试人名字
    private Member taskTester;//测试人员
    private Integer taskRelateProjectId;//关联项目id
    private String taskRelateProjectName;//关联项目名称
    private Project taskRelateProject;//关联的项目
    private String taskType;//任务类型(1.任务,2.需求,3.bug)
    private String taskStartTime;//任务开始时间
    private String taskEndTime;//任务结束时间
    private String taskStatus;//任务状态(1.待办的2.进行中3.已完成)
    private String taskContent;//任务内容
    private Integer taskSchedule;//任务进度
    private Boolean taskPostpone;//是否延期

    public void setTaskEndTime(String taskEndTime){
        this.taskEndTime=taskEndTime;
       this.taskPostpone =localTime.isPostpone(taskEndTime);
    }

    public void setTaskEndTimeOther(String taskEndTime){
        this.taskEndTime=taskEndTime;
    }
}

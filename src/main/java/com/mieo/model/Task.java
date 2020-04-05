package com.mieo.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Data
@Alias("task")
@Component
public class Task implements Serializable {
    private Integer taskId;
    private String taskName;
    private String taskPriority;//任务优先级（0.默认优先级1.不重要,2.次要,3.主要,4.严重）
    private Integer taskCreateId;//创建者id
    private Member taskCreator;//创建者
    private Integer taskExecuteId;//执行人id
    private Member taskExecutor;//执行人
    private Integer taskRelateProjectId;//关联项目id
    private Project taskRelateProject;//关联的项目
    private String taskType;//任务类型(1.任务,2.需求,3.bug)
    private String taskStartTime;//任务开始时间
    private String taskEndTime;//任务结束时间
    private String taskStatus;//任务状态(1.待办的2.进行中3.已完成)
    private String taskContent;//任务内容
    private Integer taskSchedule;//任务进度
}

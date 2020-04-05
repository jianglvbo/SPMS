package com.mieo.mapper;

import com.mieo.model.Task;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskMapper {

    /**
     * 添加任务
     *
     * @param task 任务
     */
    @Insert("insert into task (task_name, task_priority, task_create_id, task_execute_id, task_relate_project_id, task_type, " +
            "                  task_start_time, task_end_time, task_status, task_content, task_schedule) " +
            " VALUES (#{taskName},#{taskPriority},#{taskCreateId},#{taskExecuteId},#{taskRelateProjectId}," +
            " #{taskType},#{taskStartTime},#{taskEndTime},#{taskStatus},#{taskContent},#{taskSchedule})")
    @Options(useGeneratedKeys = true,keyProperty = "taskId",keyColumn = "task_id")
    void addTask(Task task);

    /**
     * 通过任务id删除任务
     *
     * @param taskId 任务id
     */
    @Delete("delete from task where task_id=#{taskId}")
    void deleteTaskByTaskId(int taskId);

    /**
     * 修改任务
     *
     * @param task 任务
     */
    @Update("update task set task_name=#{taskName}," +
            "task_priority=#{taskPriority}," +
            "task_create_id=#{taskCreateId}," +
            "task_execute_id=#{taskExecuteId}," +
            "task_relate_project_id=#{taskRelateProjectId}," +
            "task_type=#{taskType}," +
            "task_start_time=#{taskStartTime}," +
            "task_end_time=#{taskEndTime}," +
            "task_status=#{taskStatus}," +
            "task_content=#{taskContent}," +
            "task_schedule=#{taskSchedule} where task_id=#{taskId}")
    @Options(useGeneratedKeys = true,keyProperty = "taskId",keyColumn = "task_id")
    void updateTask(Task task);

    /**
     * 查询所有的任务信息
     *
     * @return 所有任务信息
     */
    @Select("select * from task")
    @Results(id = "task_all_info", value = {
            @Result(property = "taskCreator", column = "task_create_id", one = @One(select = "com.mieo.mapper.MemberMapper.queryMemberById")),
            @Result(property = "taskExecutor", column = "task_execute_id", one = @One(select = "com.mieo.mapper.MemberMapper.queryMemberById")),
            @Result(property = "taskRelateProject", column = "task_relate_project_id", one = @One(select = "com.mieo.mapper.ProjectMapper.queryProjectByProjectId")),
            @Result(property = "taskFather", column = "task_father_id", one = @One(select = "com.mieo.mapper.TaskMapper.queryTaskDetailByTaskId"))
    })
    List<Task> queryTaskAll();

    /**
     * 通过用户id查询用户相关的任务
     *
     * @param memberId 用户的id
     * @return 用户正在处理的任务
     */
    @Select("SELECT * FROM task WHERE task_create_id=#{memberId} or task_execute_id=#{memberId}")
    @ResultMap("task_all_info")
    List<Task> queryTaskByMemberId(int memberId);


    /**
     * 通过任务id查询任务详情
     *
     * @param taskId 任务id
     * @return 任务详情
     */
    @Select("SELECT * FROM task WHERE task_id=#{taskId}")
    @ResultMap("task_all_info")
    Task queryTaskDetailByTaskId(int taskId);

}

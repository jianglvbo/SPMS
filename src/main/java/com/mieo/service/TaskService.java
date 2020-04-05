package com.mieo.service;

import com.mieo.model.Task;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {

    /**
     * 添加任务
     *
     * @param task 任务
     */
    void addTask(Task task);

    /**
     * 通过任务id删除任务
     *
     * @param taskId 任务id
     */
    void deleteTaskByTaskId(int taskId);

    /**
     * 查询所有的任务信息
     *
     * @return 所有任务信息
     */
    public List<Task> queryTaskAll();

    /**
     * 通过用户id查询用户的所有任务
     *
     * @param memberId 用户的id
     * @return 用户的任务
     */
    List<Task> queryTaskByMemberId(int memberId);


    /**
     * 添加任务
     *
     * @param task 任务
     */
    void updateTask(Task task);

    /**
     * 通过任务id查询任务详情
     *
     * @param taskId 任务id
     * @return 任务详情
     */
    Task queryTaskDetailByTaskId(int taskId);
}

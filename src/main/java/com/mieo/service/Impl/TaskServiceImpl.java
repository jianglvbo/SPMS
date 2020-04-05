package com.mieo.service.Impl;

import com.mieo.common.util.DingTalkUtil;
import com.mieo.mapper.TaskMapper;
import com.mieo.model.Comment;
import com.mieo.model.DynamicState;
import com.mieo.model.Task;
import com.mieo.service.CommentService;
import com.mieo.service.DynamicStateService;
import com.mieo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    DynamicStateService dynamicStateService;
    @Autowired
    CommentService commentService;
    @Autowired
    DingTalkUtil dingTalkUtil;

    /**
     * 添加任务
     *
     * @param task 任务
     */
    @Override
    public void addTask(Task task) {
        taskMapper.addTask(task);
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("创建任务:   " + task.getTaskName(), "  " + task.getTaskContent(), "http://localhost/task/toTaskDetail?taskId=" + task.getTaskId());
        //动态
        DynamicState dynamicState = new DynamicState("添加了任务", task.getTaskName(), "任务", task.getTaskId());
        dynamicStateService.addDynamicState(dynamicState);
    }

    /**
     * 通过任务id删除任务
     *
     * @param taskId 任务id
     */
    @Override
    public void deleteTaskByTaskId(int taskId) {
        Task task = taskMapper.queryTaskDetailByTaskId(taskId);
        //钉钉
        dingTalkUtil.dingTalkTextAtOne("删除任务:  " + task.getTaskName());
        //删除动态
        DynamicState dynamicState1=new DynamicState("删除了任务",task.getTaskName(),"普通类型",taskId);
        DynamicState dynamicState2 = new DynamicState("", "", "任务", taskId);
        dynamicStateService.deleteDynamicStateByDynamicState(dynamicState2);
        dynamicStateService.addDynamicState(dynamicState1);
        //删除任务对应的评论
        Comment comment = new Comment("", "任务", taskId);
        commentService.deleteCommentByComment(comment);
        taskMapper.deleteTaskByTaskId(taskId);
    }

    /**
     * 查询所有的任务信息
     *
     * @return 所有任务信息
     */
    public List<Task> queryTaskAll() {
        return taskMapper.queryTaskAll();
    }

    /**
     * 通过用户id查询用户的所有任务
     *
     * @param memberId 用户的id
     * @return 用户的任务
     */
    @Override
    public List<Task> queryTaskByMemberId(int memberId) {
        return taskMapper.queryTaskByMemberId(memberId);
    }

    /**
     * 修改任务
     *
     * @param task 任务
     */
    @Override
    public void updateTask(Task task) {
        taskMapper.updateTask(task);
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("修改任务:  "+task.getTaskName(), task.getTaskContent(), "http://localhost/task/toTaskDetail?taskId="+task.getTaskId());
        //动态
        DynamicState dynamicState=new DynamicState("修改了任务",task.getTaskName(),"任务",task.getTaskId());
        dynamicStateService.addDynamicState(dynamicState);
    }

    /**
     * 通过任务id查询任务详情
     *
     * @param taskId 任务id
     * @return 任务详情
     */
    @Override
    public Task queryTaskDetailByTaskId(int taskId) {
        return taskMapper.queryTaskDetailByTaskId(taskId);
    }
}

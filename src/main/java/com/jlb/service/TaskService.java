package com.jlb.service;

import com.jlb.model.Task;

import java.util.List;
import java.util.Map;

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
     List<Task> queryTaskAll();

    /**
     * 查询所有的已归档任务
     * @return
     */
    List<Task> queryTaskArchiveAll();

    /**
     * 通过任务id查询归档的任务信息
     * @return
     */
    Task queryTaskArchiveByTaskId(int taskId);
    /**
     * 查询项目下的所有任务
     *
     * @param projectId
     * @return
     */
    List<Task> queryTaskAllByProjectId(int projectId);

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

    /**
     * 查询项目关联的任务数
     *
     * @param projectId
     * @return
     */
    int queryTaskCountByProjectId(int projectId);

    /**
     * 通过用户id和角色查询用户的所有任务
     *
     * @param memberId 用户的id
     * @return 用户的任务
     */
    List<Task> queryTaskByMemberIdAndRole(int memberId, int role);

    /**
     * 通过用户id查询用户的所有任务
     * @param memberId
     * @return
     */
    List<Task> queryTaskByMemberId(int memberId);

    /**
     * 查询用户下的任务数
     *
     * @param memberId
     * @return
     */
    int queryTaskCountByMemberId(int memberId, int role);

    /**
     * 根据任务是否完成数量
     *
     * @return
     */
    Map<String, Integer> queryTaskIsFinishCount(List<Task> list);

    /**
     * 查询任务是否逾期数量
     *
     * @return
     */
    Map<String, Integer> queryTaskIsPostponeCount(List<Task> list);

    /**
     * 根据任务状态查询对应任务数量
     *
     * @return
     */
    Map<String, Integer> queryTaskStatusCount(List<Task> list);

    /**
     * 根据任务优先级查询对应任务数量
     *
     * @return
     */
    Map<String, Integer> queryTaskPriorityCount(List<Task> list);

    /**
     * 通过项目id查询未归档的的任务数
     * @param projectId
     * @return
     */
    int queryTaskNotArchiveCountByProjectId(int projectId);

    /**
     * 通过任务id归档任务
     * @param taskId
     */
    void archiveTaskByTaskId(int taskId);

    /**
     * 通过任务id归档任务
     * @param taskIds
     * @return
     */
    Map<String,String> archiveTaskByTaskIds(List<Integer> taskIds);
}

package com.jlb.service.Impl;

import com.jlb.common.util.DingTalkUtil;
import com.jlb.mapper.TaskMapper;
import com.jlb.model.Comment;
import com.jlb.model.DynamicState;
import com.jlb.model.Project;
import com.jlb.model.Task;
import com.jlb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    ProjectService projectService;
    @Autowired
    MemberService memberService;

    /**
     * 添加任务
     *
     * @param task 任务
     */
    @Override
    public void addTask(Task task) {
        String taskCreateName = memberService.queryMemberNameByMemberId(task.getTaskCreateId());
        String taskExecuteName = memberService.queryMemberNameByMemberId(task.getTaskExecuteId());
        String taskTestName = memberService.queryMemberNameByMemberId(task.getTaskTestId());
        String relateProjectName = projectService.queryProjectNameByProjectId(task.getTaskRelateProjectId());
        task.setTaskExecuteName(taskExecuteName);
        task.setTaskCreateName(taskCreateName);
        task.setTaskTestName(taskTestName);
        task.setTaskRelateProjectName(relateProjectName);
        taskMapper.addTask(task);
        //钉钉
        dingTalkUtil.dingTalkLinkAtOne("创建任务:   " + task.getTaskName(),  task.getTaskContent(), "http://localhost/task/toTaskDetail?taskId=" + task.getTaskId());
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
        DynamicState dynamicState1 = new DynamicState("删除了任务", task.getTaskName(), "普通类型", taskId);
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
     * 查询所有的已归档任务
     *
     * @return
     */
    @Override
    public List<Task> queryTaskArchiveAll() {
        return taskMapper.queryTaskArchiveAll();
    }

    /**
     * 通过角色和id查看归档任务
     *
     * @param memberId
     * @param role
     */
    @Override
    public List<Task> queryTaskAllArchiveByMemberIdAndRole(int memberId, int role) {
        if (role == 1) {
            //如果是管理员，查询所有的已归档任务
            return taskMapper.queryTaskArchiveAll();
        }
        if (role == 2) {
            //如果是项目经理。查询负责项目下的所有已归档任务
            List<Project> projects = projectService.queryProjectArchiveByMemberIdAndRole(memberId, role);
            List<Task> tasks = new ArrayList<>();
            for (Project project : projects) {
                tasks.addAll(taskMapper.queryTaskArchiveByProjectId(project.getProjectId()));
            }
            return tasks;
        } else {
            //研发和测试人员查询所有相关任务
            return taskMapper.queryTaskArchiveByMemberId(memberId);
        }
    }

    /**
     * 通过任务id查询归档的任务信息
     *
     * @param taskId
     * @return
     */
    @Override
    public Task queryTaskArchiveByTaskId(int taskId) {
        return taskMapper.queryTaskArchiveByTaskId(taskId);
    }

    /**
     * 查询项目下的所有任务
     *
     * @param projectId
     * @return
     */
    @Override
    public List<Task> queryTaskAllByProjectId(int projectId) {
        return taskMapper.queryTaskAllByProjectId(projectId);
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
        dingTalkUtil.dingTalkLinkAtOne("修改任务:  " + task.getTaskName(), task.getTaskContent(), "http://localhost/task/toTaskDetail?taskId=" + task.getTaskId());
        //动态
        DynamicState dynamicState = new DynamicState("修改了任务", task.getTaskName(), "任务", task.getTaskId());
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

    /**
     * 查询项目关联的任务数
     *
     * @param projectId
     * @return
     */
    @Override
    public int queryTaskCountByProjectId(int projectId) {
        return taskMapper.queryTaskCountByProjectId(projectId);
    }

    /**
     * 通过用户id查询用户的所有任务
     *
     * @param memberId 用户的id
     * @return 用户的任务
     */
    @Override
    public List<Task> queryTaskByMemberIdAndRole(int memberId, int role) {
        if (role == 1) {
            //如果是管理员，查询所有的未归档任务
            return taskMapper.queryTaskAllNotArchive();
        }
        if (role == 2) {
            //如果是项目经理。查询负责项目下的所有未归档任务
            List<Project> projects = projectService.queryProjectByMemberIdAndRole(memberId, role);
            List<Task> tasks = new ArrayList<>();
            for (Project project : projects) {
                tasks.addAll(taskMapper.queryTaskByProjectId(project.getProjectId()));
            }
            return tasks;
        } else {
            //研发和测试人员查询所有相关任务
            return taskMapper.queryTaskByMemberId(memberId);
        }
    }

    /**
     * 通过用户id查询用户的所有任务
     *
     * @param memberId
     * @return
     */
    @Override
    public List<Task> queryTaskByMemberId(int memberId) {
        return taskMapper.queryTaskByMemberId(memberId);
    }

    /**
     * 查询用户下的任务数
     *
     * @param memberId
     * @return
     */
    @Override
    public int queryTaskCountByMemberIdAndRole(int memberId, int role) {
        if (role == 1) {
            return taskMapper.queryTaskCountAll();
        }
        if (role == 2) {
            List<Project> projects = projectService.queryProjectByMemberIdAndRole(memberId, role);
            int count = 0;
            for (Project project : projects) {
                count += taskMapper.queryTaskCountByProjectId(project.getProjectId());
            }
//           count+=taskMapper.queryTaskCountByMemberId(memberId);
            return count;
        } else {
            return taskMapper.queryTaskCountByMemberId(memberId);
        }
    }

    /**
     * 根据任务是否完成数量
     *
     * @return
     */
    @Override
    public Map<String, Integer> queryTaskIsFinishCount(List<Task> list) {
        int finish = 0;
        for (Task task : list) {
            if (task.getTaskStatus().equalsIgnoreCase("4")) {
                finish++;
            }
        }
        int notFinish = list.size() - finish;
        Map<String, Integer> map = new HashMap<>();
        map.put("finish", finish);
        map.put("notFinish", notFinish);
        return map;
    }

    /**
     * 查询任务是否逾期数量
     *
     * @return
     */
    @Override
    public Map<String, Integer> queryTaskIsPostponeCount(List<Task> list) {
        int postpone = 0;
        for (Task task : list) {
            if (task.getTaskPostpone()) {
                postpone++;
            }
        }
        int notPostpone = list.size() - postpone;
        Map<String, Integer> map = new HashMap<>();
        map.put("postpone", postpone);
        map.put("notPostpone", notPostpone);
        return map;
    }

    /**
     * 根据任务状态查询对应任务数量
     *
     * @return
     */
    @Override
    public Map<String, Integer> queryTaskStatusCount(List<Task> list) {
        int acting = 0;
        int underway = 0;
        int test = 0;
        int finish = 0;
        for (Task task : list) {
            if (task.getTaskStatus().equalsIgnoreCase("1")) {
                acting++;
            }
            if (task.getTaskStatus().equalsIgnoreCase("2")) {
                underway++;
            }
            if (task.getTaskStatus().equalsIgnoreCase("3")) {
                test++;
            }
            if (task.getTaskStatus().equalsIgnoreCase("4")) {
                finish++;
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("acting", acting);
        map.put("underway", underway);
        map.put("finish", finish);
        map.put("test", test);
        return map;
    }

    /**
     * 根据任务优先级查询对应任务数量
     *
     * @return
     */
    @Override
    public Map<String, Integer> queryTaskPriorityCount(List<Task> list) {
        int noImportant = 0;
        int secondary = 0;
        int main = 0;
        int seriousness = 0;
        for (Task task : list) {
            if (task.getTaskPriority().equalsIgnoreCase("1")) {
                noImportant++;
            }
            if (task.getTaskPriority().equalsIgnoreCase("2")) {
                secondary++;
            }
            if (task.getTaskPriority().equalsIgnoreCase("3")) {
                main++;
            }
            if (task.getTaskPriority().equalsIgnoreCase("4")) {
                seriousness++;
            }
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("noImportant", noImportant);
        map.put("secondary", secondary);
        map.put("main", main);
        map.put("seriousness", seriousness);
        return map;
    }

    /**
     * 通过项目id查询未归档的的任务数
     *
     * @param projectId
     * @return
     */
    @Override
    public int queryTaskNotArchiveCountByProjectId(int projectId) {
        return taskMapper.queryTaskNotArchiveCountByProjectId(projectId);
    }

    /**
     * 通过任务id归档任务
     *
     * @param taskId
     */
    @Override
    public void archiveTaskByTaskId(int taskId) {
        Task task = taskMapper.queryTaskDetailByTaskId(taskId);
        //钉钉
        dingTalkUtil.dingTalkTextAtOne("归档任务:  " + task.getTaskName());
        //删除动态
        DynamicState dynamicState1 = new DynamicState("归档了任务", task.getTaskName(), "普通类型", taskId);
        DynamicState dynamicState2 = new DynamicState("", "", "任务", taskId);
        dynamicStateService.deleteDynamicStateByDynamicState(dynamicState2);
        dynamicStateService.addDynamicState(dynamicState1);
        //删除任务对应的评论
        Comment comment = new Comment("", "任务", taskId);
        commentService.deleteCommentByComment(comment);
        taskMapper.archiveTaskByTaskId(taskId);
    }

    /**
     * 通过任务id归档任务
     *
     * @param taskIds
     * @return
     */
    @Override
    public Map<String, String> archiveTaskByTaskIds(List<Integer> taskIds) {
        Map<String, String> msg = new HashMap<>();
        msg.put("temp", "success");
        msg.put("msg", "归档完成");
        //验证数据合理性
        for (Integer taskId : taskIds) {
            Task task = taskMapper.queryTaskByTaskId(taskId);
            if (task.getTaskSchedule() != 100) {
                msg.put("temp", "danger");
                msg.put("msg", "任务进度不到100%");
                return msg;
            } else if (!task.getTaskStatus().equals("4")) {
                msg.put("temp", "danger");
                msg.put("msg", "任务状态需要为已完成");
                return msg;
            }
        }
        for (Integer taskId : taskIds) {
            Task task = taskMapper.queryTaskDetailByTaskId(taskId);
            //钉钉
            dingTalkUtil.dingTalkTextAtOne("归档任务:  " + task.getTaskName());
            //删除动态
            DynamicState dynamicState1 = new DynamicState("归档了任务", task.getTaskName(), "普通类型", taskId);
            DynamicState dynamicState2 = new DynamicState("", "", "任务", taskId);
            dynamicStateService.deleteDynamicStateByDynamicState(dynamicState2);
            dynamicStateService.addDynamicState(dynamicState1);
            //删除任务对应的评论
            Comment comment = new Comment("", "任务", taskId);
            commentService.deleteCommentByComment(comment);
            taskMapper.archiveTaskByTaskId(taskId);
        }
        return msg;
    }
}

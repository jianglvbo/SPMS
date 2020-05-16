package com.jlb.controller;

import com.jlb.model.Task;
import com.jlb.model.util.BurnDown;
import com.jlb.service.DynamicStateService;
import com.jlb.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Slf4j
@RequestMapping("task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    DynamicStateService dynamicStateService;

    /**
     * 添加任务
     *
     * @param task 任务
     */
    @RequestMapping("addTask")
    @ResponseBody
    Map<String, String> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "添加任务成功");
        return map;
    }

    /**
     * 通过任务id删除任务
     *
     * @param taskIds
     */
    @RequestMapping("removeTaskByTaskIds")
    @ResponseBody
    Map<String, String> removeTaskByTaskIds(@RequestBody List<Integer> taskIds) {
        for (Integer taskId : taskIds) {
            taskService.deleteTaskByTaskId(taskId);
        }
        Map<String, String> msg = new HashMap<>();
        msg.put("msg", "删除成功");
        msg.put("temp", "success");
        return msg;
    }

    /**
     * 查询所有的任务信息
     *
     * @return 所有的任务信息
     */
    @RequestMapping("queryTaskAll")
    @ResponseBody
    public List<Task> queryTaskAll() {
        return taskService.queryTaskAll();
    }

    @RequestMapping("queryTaskArchiveAll")
    @ResponseBody
    public List<Task> queryTaskArchiveAll() {
        return taskService.queryTaskArchiveAll();
    }

    /**
     * 通过用户id查询用户的任务
     *
     * @return 用户正在处理的任务
     */
    @RequestMapping("queryTaskByMemberIdAndRole")
    @ResponseBody
    List<Task> queryTaskByMemberIdAndRole(int memberId, int role) {
        return taskService.queryTaskByMemberIdAndRole(memberId, role);
    }


    /**
     * 通过用户id查询用户的任务
     *
     * @return 用户正在处理的任务
     */
    @RequestMapping("queryTaskByMemberId")
    @ResponseBody
    List<Task> queryTaskByMemberIdAndRole(int memberId) {
        return taskService.queryTaskByMemberId(memberId);
    }


    /**
     * 主页任务饼状图数据
     *
     * @return 用户正在处理的任务
     */
    @RequestMapping("queryTaskPieCountByMemberIdAndRole")
    @ResponseBody
    Map<String, Integer> queryTaskPieCountByMemberIdAndRole(int memberId, int role) {
        List<Task> list = taskService.queryTaskByMemberIdAndRole(memberId, role);
        Map<String, Integer> map1 = taskService.queryTaskPriorityCount(list);
        Map<String, Integer> map2 = taskService.queryTaskStatusCount(list);
        Map<String, Integer> map3 = taskService.queryTaskIsFinishCount(list);
        Map<String, Integer> map4 = taskService.queryTaskIsPostponeCount(list);
        Map<String, Integer> map = new HashMap<>();
        map.putAll(map1);
        map.putAll(map2);
        map.putAll(map3);
        map.putAll(map4);
        return map;
    }

    /**
     * 项目任务饼状图数据
     *
     */
    @RequestMapping("queryTaskPieCountByProjectId")
    @ResponseBody
    Map<String, Integer> queryTaskPieCountByProjectId(int projectId) {
        List<Task> list = taskService.queryTaskAllByProjectId(projectId);
        Map<String, Integer> map1 = taskService.queryTaskPriorityCount(list);
        Map<String, Integer> map2 = taskService.queryTaskStatusCount(list);
        Map<String, Integer> map3 = taskService.queryTaskIsFinishCount(list);
        Map<String, Integer> map4 = taskService.queryTaskIsPostponeCount(list);
        Map<String, Integer> map = new HashMap<>();
        map.putAll(map1);
        map.putAll(map2);
        map.putAll(map3);
        map.putAll(map4);
        return map;
    }

    @RequestMapping("queryTaskBurnDownByProjectId")
    @ResponseBody
    Map<String, List<BurnDown>> queryTaskBurnDownByProjectId(int projectId) {
        List<Task> tasks = taskService.queryTaskAllByProjectId(projectId);
        Date date;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        List<BurnDown> list = new ArrayList<>();
        List<BurnDown> list1 = new ArrayList<>();
        for (Task task : tasks) {
            //时间格式转换
            try {
                Date p1 = df.parse(task.getTaskStartTime());
                task.setTaskStartTime(df.format(p1));
                Date p2 = df.parse(task.getTaskEndTime());
                task.setTaskEndTimeOther(df.format(p2));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //链表初始化
            if (list1.size() == 0 && task.getTaskStatus().equals("3")) {
                BurnDown burnDown = new BurnDown();
                burnDown.setDate(task.getTaskStartTime());
                burnDown.setTaskCount(1);
                list1.add(burnDown);
                burnDown = new BurnDown();
                burnDown.setDate(task.getTaskEndTime());
                burnDown.setTaskCount(1);
                list1.add(burnDown);
            }
            if (list.size() == 0) {
                BurnDown burnDown = new BurnDown();
                burnDown.setDate(task.getTaskStartTime());
                burnDown.setTaskCount(1);
                list.add(burnDown);
                burnDown = new BurnDown();
                burnDown.setDate(task.getTaskEndTime());
                burnDown.setTaskCount(1);
                list.add(burnDown);
            }
            //计划任务
            if (list.size() > 0) {
                List temp1 = new ArrayList();
                //链表中是否已存在该时间点
                for (BurnDown burnDown : list1) {
                    //存在开始时间点
                    if (burnDown.getDate().equals(task.getTaskStartTime())) {
                        Integer taskCount = burnDown.getTaskCount();
                        burnDown.setTaskCount(taskCount + 1);
                        temp1.add(burnDown);
                    } else {
                        //不存在开始时间点
                        BurnDown burnDown1 = new BurnDown();
                        burnDown1.setDate(task.getTaskStartTime());
                        burnDown1.setTaskCount(1);
                        temp1.add(burnDown1);
                    }
                    //存在终止时间点
                    if (burnDown.getDate().equals(task.getTaskEndTime())) {
                        Integer taskCount = burnDown.getTaskCount();
                        burnDown.setTaskCount(taskCount + 1);
                        temp1.add(burnDown);
                    } else {
                        //不存在终止时间点
                        BurnDown burnDown1 = new BurnDown();
                        burnDown1.setDate(task.getTaskEndTime());
                        burnDown1.setTaskCount(1);
                        temp1.add(burnDown1);
                    }
                }
                list = temp1;
            }

            //任务已完成
            if (list1.size() > 0 && task.getTaskStatus().equalsIgnoreCase("3")) {
                List temp2 = new ArrayList();
                for (BurnDown burnDown : list1) {
                    //链表中是否已存在该时间点
                    //链表中是否已存在该时间点
                    //存在开始时间点
                    if (burnDown.getDate().equals(task.getTaskStartTime())) {
                        Integer taskCount = burnDown.getTaskCount();
                        burnDown.setTaskCount(taskCount + 1);
                        temp2.add(burnDown);
                    } else {
                        //不存在开始时间点
                        BurnDown burnDown1 = new BurnDown();
                        burnDown1.setDate(task.getTaskStartTime());
                        burnDown1.setTaskCount(1);
                        temp2.add(burnDown1);
                    }
                    //存在终止时间点
                    if (burnDown.getDate().equals(task.getTaskEndTime())) {
                        Integer taskCount = burnDown.getTaskCount();
                        burnDown.setTaskCount(taskCount + 1);
                        temp2.add(burnDown);
                    } else {
                        //不存在终止时间点
                        BurnDown burnDown1 = new BurnDown();
                        burnDown1.setDate(task.getTaskEndTime());
                        burnDown1.setTaskCount(1);
                        temp2.add(burnDown1);
                    }
                }
                list1 = temp2;
            }
        }
        Map<String, List<BurnDown>> map = new HashMap<>();
        map.put("finish", list1);
        map.put("all", list);
        return map;
    }

    /**
     * 跳转到任务详情页面
     *
     * @param taskId 任务id
     */
    @RequestMapping("toTaskDetail")
    ModelAndView toTaskDetail(int taskId) {
        ModelAndView modelAndView = new ModelAndView();
        Task task = taskService.queryTaskDetailByTaskId(taskId);
        modelAndView.setViewName("task_detail");
        modelAndView.addObject("task", task);
        return modelAndView;
    }

    /**
     * 编辑任务
     *
     * @param task 任务
     */
    @RequestMapping("updateTask")
    @ResponseBody
    Map<String, String> updateTask(@RequestBody Task task) {
        taskService.updateTask(task);
        Map<String, String> map = new HashMap<>();
        map.put("msg", "修改成功");
        return map;
    }


    @RequestMapping("toTask")
    public String toTask() {
        return "task";
    }

    @RequestMapping("toTaskAdd")
    public String toTaskAdd() {
        return "task_add";
    }

    @RequestMapping("toTaskArchiveDetail")
    public ModelAndView toTaskArchiveDetail(int taskId) {
        ModelAndView modelAndView = new ModelAndView();
        Task task = taskService.queryTaskArchiveByTaskId(taskId);
        modelAndView.setViewName("archive_task_detail");
        modelAndView.addObject("task", task);
        return modelAndView;
    }

    @RequestMapping("queryTaskArchiveDetail")
    @ResponseBody
    public Task queryTaskArchiveDetail(int taskId) {
        return taskService.queryTaskArchiveByTaskId(taskId);
    }

    @RequestMapping("archiveTaskByTaskIds")
    @ResponseBody
    public Map<String, String> archiveTaskByTaskIds(@RequestBody List<Integer> taskIds) {
        return taskService.archiveTaskByTaskIds(taskIds);
    }
}

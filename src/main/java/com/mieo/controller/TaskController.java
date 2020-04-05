package com.mieo.controller;

import com.mieo.model.Task;
import com.mieo.service.DynamicStateService;
import com.mieo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param taskIds
     */
    @RequestMapping("removeTaskByTaskIds")
    @ResponseBody
    Map<String,String> removeTaskByTaskIds(@RequestBody List<Integer> taskIds){
        for (Integer taskId : taskIds) {
            taskService.deleteTaskByTaskId(taskId);
        }
        Map<String,String> map=new HashMap<>();
        map.put("msg", "删除成功");
        return map;
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

    /**
     * 通过用户id查询用户的任务
     *
     * @return 用户正在处理的任务
     */
    @RequestMapping("queryTaskByMemberId")
    @ResponseBody
    List<Task> queryTaskByMemberId(int memberId) {
        return taskService.queryTaskByMemberId(memberId);
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
        log.debug("跳转到任务页面");
        return "task";
    }

    @RequestMapping("toTaskAdd")
    public String toTaskAdd() {
        log.debug("跳转到任务添加页面");
        return "task_add";
    }

}

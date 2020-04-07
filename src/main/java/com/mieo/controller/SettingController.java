package com.mieo.controller;

import com.mieo.common.util.SettingUtil;
import com.mieo.model.Setting;
import com.mieo.service.SettingService;
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

@Slf4j
@Controller
@RequestMapping("setting")
public class SettingController {
    @Autowired
    SettingService settingService;
    @Autowired
    SettingUtil settingUtil;

    @RequestMapping("toSetting")
    public ModelAndView toSetting() {
        ModelAndView modelAndView=new ModelAndView();
        List<Setting> list = settingService.querySettingAll();
        Map<String, String> settingMap = settingUtil.getSettingMap(list);
        modelAndView.addObject("setting",settingMap);
        modelAndView.setViewName("setting");
        return modelAndView;
    }

    @RequestMapping("querySettingAll")
    @ResponseBody
    public Map<String,Object> querySettingAll() {
        Map<String, Object> setting = settingUtil.getSetting();
        return setting;
    }

    @RequestMapping("updateSetting")
    @ResponseBody
    public Map<String,String> updateSetting(@RequestBody Map<String,String> map) {
        Map<String,String> msg=new HashMap<>();
        List<Setting> settingList = settingUtil.getSettingList(map);
        for (Setting setting : settingList) {
            settingService.updateSetting(setting);
        }
        msg.put("msg", "修改成功");
        return msg;
    }


}

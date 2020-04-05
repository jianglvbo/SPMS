package com.mieo.controller;

import com.mieo.common.util.SettingUtil;
import com.mieo.model.Setting;
import com.mieo.service.SettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String toSetting() {
        return "setting";
    }

    @RequestMapping("querySettingAll")
    @ResponseBody
    public Map<String,Object> querySettingAll() {
        Map<String, Object> setting = settingUtil.getSetting();
        return setting;
    }
}

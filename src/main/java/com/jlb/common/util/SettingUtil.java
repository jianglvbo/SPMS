package com.jlb.common.util;

import com.jlb.model.Setting;
import com.jlb.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SettingUtil {
    @Autowired
    SettingService settingService;

    public Map<String, Object> getSetting() {
        Map<String, Object> map = new HashMap<>();
        List<Setting> list = settingService.querySettingAll();
        for (Setting setting : list) {
            map.put(setting.getSettingName(), setting.getSettingValue());
        }
        return map;
    }

    public Map<String, String> getSettingMap(List<Setting> list) {
        Map<String, String> map = new HashMap<>();
        for (Setting setting : list) {
            map.put(setting.getSettingName(), setting.getSettingValue());
        }
        return map;
    }

    public List<Setting> getSettingList(Map<String, String> map) {
        List<Setting> list = new ArrayList<>();
        list = listAdd(list, map, "dingTalkWebHook");
        list = listAdd(list, map, "dingTalkSecretKey");
        list = listAdd(list, map, "messageAppId");
        list = listAdd(list, map, "messageAppKey");
        list = listAdd(list, map, "messageLoginTemplateId");
        list = listAdd(list, map, "messageSign");
        list = listAdd(list, map, "messageAlterTemplateId");
        list = listAdd(list, map, "iconLocation");
        return list;
    }

    private List<Setting> listAdd(List<Setting> list, Map<String, String> map, String key) {
        Setting setting = new Setting();
        setting.setSettingName(key);
        setting.setSettingValue(map.get(key));
        list.add(setting);
        return list;
    }
}

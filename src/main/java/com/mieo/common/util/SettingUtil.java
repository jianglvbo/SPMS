package com.mieo.common.util;

import com.mieo.model.Setting;
import com.mieo.service.SettingService;
import lombok.Data;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SettingUtil {
    @Autowired
    SettingService settingService;

    public Map<String,Object> getSetting(){
        Map<String, Object> map = new HashMap<>();
        List<Setting> list = settingService.querySettingAll();
        for (Setting setting : list) {
            map.put(setting.getSettingName(), setting.getSettingValue());
        }
        return map;
    }
}

package com.mieo.service;


import com.mieo.model.Setting;

import java.util.List;

public interface SettingService {
    /**
     * 查询配置的所有内容
     * @return
     */
    List<Setting> querySettingAll();
}

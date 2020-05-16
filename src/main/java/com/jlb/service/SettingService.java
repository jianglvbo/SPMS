package com.jlb.service;


import com.jlb.model.Setting;

import java.util.List;
import java.util.Map;

public interface SettingService {
    /**
     * 查询配置的所有内容
     * @return
     */
    List<Setting> querySettingAll();

    /**
     * 修改配置信息
     */
    void updateSetting(Setting setting);

    /**
     * 修改公告信息
     */
    Map<String,String> updateSettingAnnouncement(Map<String,String> map);

    /**
     * 查询公告信息
     */
    Map<String,String> querySettingAnnouncement();
}

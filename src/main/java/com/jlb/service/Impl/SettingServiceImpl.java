package com.jlb.service.Impl;

import com.jlb.mapper.SettingMapper;
import com.jlb.model.Setting;
import com.jlb.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    SettingMapper settingMapper;
    /**
     * 查询配置的所有内容
     *
     * @return
     */
    @Override
    public List<Setting> querySettingAll() {
        return settingMapper.querySettingAll();
    }

    /**
     * 修改配置信息
     */
    @Override
    public void updateSetting(Setting setting) {
        settingMapper.updateSetting(setting);
    }

    /**
     * 修改公告信息
     *
     * @param map
     */
    @Override
    public Map<String,String> updateSettingAnnouncement(Map<String, String> map) {
        Map<String,String> msg=new HashMap<>();
        settingMapper.updateSettingAnnouncement(map);
        msg.put("msg", "发布成功");
        msg.put("temp", "success");
        return msg;
    }

    /**
     * 查询公告信息
     */
    @Override
    public Map<String, String> querySettingAnnouncement() {
        return settingMapper.querySettingAnnouncement();
    }
}

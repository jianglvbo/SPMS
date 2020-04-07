package com.mieo.service.Impl;

import com.mieo.mapper.SettingMapper;
import com.mieo.model.Setting;
import com.mieo.service.SettingService;
import org.apache.http.cookie.SM;
import org.apache.shiro.session.mgt.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


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
}

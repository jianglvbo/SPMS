package com.mieo.mapper;

import com.mieo.model.Setting;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettingMapper {

    /**
     * 查询配置的所有内容
     * @return
     */
    @Select("select * from setting")
    List<Setting> querySettingAll();

    /**
     * 修改配置信息
     */
    @Update("update setting set setting_value=#{settingValue} where setting_name=#{settingName}")
    void updateSetting(Setting setting);
}

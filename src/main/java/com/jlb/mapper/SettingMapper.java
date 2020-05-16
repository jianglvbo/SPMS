package com.jlb.mapper;

import com.jlb.model.Setting;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    /**
     * 修改公告信息
     */
    @Update("update setting set setting_value=#{announcement_content},setting_comment=#{announcement_priority} where setting_name='announcement'")
    void updateSettingAnnouncement(Map<String,String> map);

    /**
     * 查询公告信息
     */
    @Select("select * from setting where setting_name='announcement' and setting_id=9")
    Map<String,String> querySettingAnnouncement();
}

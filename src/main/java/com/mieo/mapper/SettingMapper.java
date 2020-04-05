package com.mieo.mapper;

import com.mieo.model.Setting;
import org.apache.ibatis.annotations.Select;
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
}

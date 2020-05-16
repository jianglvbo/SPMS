package com.jlb.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Setting {
    private int settingId;
    private String settingName;
    private String settingValue;

}

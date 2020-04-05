package com.mieo.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
public class Setting {
    private int settingId;
    private String settingName;
    private String settingValue;

}

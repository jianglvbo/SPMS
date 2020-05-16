package com.jlb.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SettingValue {
    private String dingTalkSecretKey;
    private String dingTalkWebHook;
    private int messageAppId;
    private String messageAppKey;
    private int messageTemplateId;
    private String messageSign;
}

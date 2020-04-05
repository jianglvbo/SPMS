package com.mieo.model.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Message {
    // 短信应用 SDK AppID
    private int appid = 1400340124; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    private String appkey = "7d286a826050b356fb0e595eaa49e211";
    // 需要发送短信的手机号码
    private String[] phoneNumbers = {"15960717193"};
    // 短信模板 ID，需要在短信应用中申请
    private int templateId = 566735; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    private String smsSign = "yoooina学习分享"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

}

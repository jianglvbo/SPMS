package com.jlb.model.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Message {
    // 短信应用 SDK AppID
    private int appid ; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    private String appkey ;
    // 短信模板 ID，需要在短信应用中申请
    private int loginTemplateId ; // NOTE: 这里的模板 ID`7839`只是示例，真实的模板 ID 需要在短信控制台中申请
    // 签名
    private String smsSign ; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

    private int alterTemplateId;//修改密码模板id

}

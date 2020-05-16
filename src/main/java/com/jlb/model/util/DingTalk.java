package com.jlb.model.util;

import com.dingtalk.api.request.OapiRobotSendRequest;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class DingTalk {
    private OapiRobotSendRequest request;
    private String secret="";
    private String serverUrl="";
}

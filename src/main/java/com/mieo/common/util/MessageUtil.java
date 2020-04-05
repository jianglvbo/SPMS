package com.mieo.common.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.mieo.model.util.Message;
import org.apache.commons.collections4.MapUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {
 @Autowired
 Message message;
 @Autowired
 SettingUtil settingUtil;

    private Message getMessage(){
        Map<String, Object> setting = settingUtil.getSetting();
        message.setAppid(MapUtils.getInteger(setting, "messageAppId"));
        message.setAppkey(MapUtils.getString(setting, "messageAppKey"));
        message.setSmsSign(MapUtils.getString(setting, "messageSign"));
        message.setTemplateId(MapUtils.getInteger(setting, "messageTemplateId"));
        return message;
    }

    public  void messageSend() {
        Message message = getMessage();
        int appid=message.getAppid();
        String appkey=message.getAppkey();
        String[] phoneNumbers = message.getPhoneNumbers();
        int templateId=message.getTemplateId();
        String smsSign=message.getSmsSign();
        try {
            String[] params = {"5678"};
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");
            System.out.println(result);
        } catch (HTTPException e) {
            // HTTP 响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // JSON 解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络 IO 错误
            e.printStackTrace();
        }
    }
}

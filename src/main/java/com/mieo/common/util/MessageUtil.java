package com.mieo.common.util;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import com.mieo.model.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.json.JSONException;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageUtil {
    @Autowired
    Message message;
    @Autowired
    SettingUtil settingUtil;

    private Message getMessage() {
        Map<String, Object> setting = settingUtil.getSetting();
        message.setAppid(MapUtils.getInteger(setting, "messageAppId"));
        message.setAppkey(MapUtils.getString(setting, "messageAppKey"));
        message.setSmsSign(MapUtils.getString(setting, "messageSign"));
        message.setLoginTemplateId(MapUtils.getInteger(setting, "messageLoginTemplateId"));
        message.setAlterTemplateId(MapUtils.getInteger(setting, "messageAlterTemplateId"));
        return message;
    }

    /**
     * 发送验证短信
     * @param verifyCode 验证码
     * @param phoneNumber 手机号
     * @param type 短信类型
     */
    public String messageSend(String verifyCode, String phoneNumber, String type) {
        Message message = getMessage();
        int appid = message.getAppid();
        String appkey = message.getAppkey();
        int alterTemplateId = message.getAlterTemplateId();
        int loginTemplateId = message.getLoginTemplateId();
        String smsSign = message.getSmsSign();
        String[] params = {verifyCode};
        SmsSingleSenderResult result=null;
        try {
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            if (type.equalsIgnoreCase("alter")) {
              result = ssender.sendWithParam("86", phoneNumber,alterTemplateId, params, smsSign, "", "");
            }else if(type.equalsIgnoreCase("login")){
                result=ssender.sendWithParam("86", phoneNumber,loginTemplateId, params, smsSign, "", "");
            }
            log.debug("短信发送: "+result.sid+" // "+result.errMsg);
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
        return result.errMsg;
    }
}

package com.mieo.common.util.dingTalk;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Slf4j
public class DingTalkText{
    private  OapiRobotSendRequest request;
    private final static String  secret="SEC43631fa8daff10c19374a4ca8d9cdddd782af14a0284a07fe8a465bd4bec5c31";
    private  String serverUrl =
            "https://oapi.dingtalk.com/robot/send?access_token=1a9438435a210f489ad74f0455887f638b65c6b56c3ef05cab31cb14c331c6bc";
    public DingTalkText(String isAtAll,List<String> mobiles,String content) {
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(isAtAll);
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        at.setAtMobiles(mobiles);
        request.setMsgtype("text");
        request.setAt(at);
        request.setText(text);
        this.request =request;
    }

    private String dingTalkEncrypt() {
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        String urlEncoder=null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
          urlEncoder = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            log.debug("调用钉钉加密失败:");
            e.printStackTrace();
        }
        serverUrl+="&timestamp="+timestamp+"";
        serverUrl+="&sign="+urlEncoder+"";
        return serverUrl;
    }

    public void dingTalkExecute(){

        DingTalkClient client = new DefaultDingTalkClient(dingTalkEncrypt());
        OapiRobotSendResponse response = new OapiRobotSendResponse();
        try {
            response = client.execute(this.request);
        } catch (ApiException e) {
            log.debug("调用钉钉api失败:");
            e.printStackTrace();
        }
        log.debug("调用钉钉api发送消息:{"+this.request.getText().toString()+"调用信息:"+response.getMsg()+"}");
    }
}


package com.jlb.common.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.jlb.model.util.DingTalk;
import com.jlb.model.Member;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class DingTalkUtil {
    @Autowired
    DingTalk dingTalk;
    @Autowired
    SettingUtil settingUtil;

    private DingTalk dingTalkText(String isAtAll, List<String> mobiles, String content) {
         Map<String,Object> map=settingUtil.getSetting();
         dingTalk.setSecret(MapUtils.getString(map, "dingTalkSecretKey"));
         dingTalk.setServerUrl(MapUtils.getString(map, "dingTalkWebHook"));
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(isAtAll);
        at.setAtMobiles(mobiles);
        OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
        text.setContent(content);
        request.setMsgtype("text");
        request.setAt(at);
        request.setText(text);
       dingTalk.setRequest(request);
       return dingTalk;
    }


    private DingTalk dingTalkLink(String isAtAll, List<String> mobiles, String title, String text, String url) {
        Map<String,Object> map=settingUtil.getSetting();
        dingTalk.setSecret(MapUtils.getString(map, "dingTalkSecretKey"));
        dingTalk.setServerUrl(MapUtils.getString(map, "dingTalkWebHook"));
        OapiRobotSendRequest request = new OapiRobotSendRequest();
        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        at.setIsAtAll(isAtAll);
        at.setAtMobiles(mobiles);
        OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
        link.setTitle(title);
        link.setText(text);
        link.setMessageUrl(url);
        request.setMsgtype("link");
        request.setAt(at);
        request.setLink(link);
        dingTalk.setRequest(request);
        return dingTalk;
    }

    private String dingTalkEncrypt(DingTalk dingTalk) {
        String secret=dingTalk.getSecret();
        String serverUrl=dingTalk.getServerUrl();
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        String urlEncoder = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            urlEncoder = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            log.debug("调用钉钉加密失败:");
            e.printStackTrace();
        }
        serverUrl += "&timestamp=" + timestamp + "";
        serverUrl += "&sign=" + urlEncoder + "";
        return serverUrl;
    }

    private void dingTalkExecute(DingTalk dingTalk) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(dingTalkEncrypt(dingTalk));
            OapiRobotSendResponse response = new OapiRobotSendResponse();
            response = client.execute(dingTalk.getRequest());
        } catch (ApiException e) {
            log.debug("调用钉钉api失败:");
            e.printStackTrace();
        }
    }

    public void dingTalkTextAtAll(String content) {
        String isAtAll = "true";
        DingTalk dingTalk = dingTalkText(isAtAll, null, content);
        dingTalkExecute(dingTalk);
    }

    public void dingTalkTextAtSome(String content, List<String> mobiles) {
        String itAtAll = "false";
        DingTalk dingTalk=dingTalkText(itAtAll, mobiles, content);
        dingTalkExecute(dingTalk);
    }

    public void dingTalkTextAtOne(String content) {
        Session session = SecurityUtils.getSubject().getSession();
        Member member = (Member) session.getAttribute("user");
        String itAtAll = "false";
        String mobile = member.getMemberPhone();
        ArrayList<String> list = new ArrayList<>();
        list.add(mobile);
        DingTalk dingTalk = dingTalkText(itAtAll, list, content);
        dingTalkExecute(dingTalk);
    }


    public void dingTalkLinkAtOne(String title, String text, String url) {
        Session session = SecurityUtils.getSubject().getSession();
        Member member = (Member) session.getAttribute("user");
        String itAtAll = "false";
        String mobile = member.getMemberPhone();
        ArrayList<String> list = new ArrayList<>();
        list.add(mobile);
        DingTalk dingTalk = dingTalkLink(itAtAll, list, title, text, url);
        dingTalkExecute(dingTalk);
    }

}

